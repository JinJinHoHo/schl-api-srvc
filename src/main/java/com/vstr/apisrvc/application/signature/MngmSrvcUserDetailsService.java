package com.vstr.apisrvc.application.signature;

import com.vstr.apisrvc.adapter.out.jpa.MngrRepository;
import com.vstr.apisrvc.adapter.out.jpa.SchulMngrMapngRepository;
import com.vstr.apisrvc.application.code.MngrTyCode;
import com.vstr.apisrvc.application.entity.MngrEntity;
import com.vstr.apisrvc.application.entity.SchulMngrMapngEntity;
import com.vstr.apisrvc.core.session.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * 운영자/관리자 UserDetailsService
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class MngmSrvcUserDetailsService implements SrvcUserDetailsService {

    private final MngrRepository mngrRepository;
    private final SchulMngrMapngRepository schulMngrMapngRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MngrEntity mngr = mngrRepository.findByMngrIdAndMngrTyCode(username, MngrTyCode.SCHUL);
        if (mngr == null) throw new UsernameNotFoundException("사용자 정보를 찾을수 없음.");

        return User.withUsername(mngr.getId().toString())
                .password(mngr.getPassword())
                .accountLocked(mngr.isCtActvtyAt())
                .roles(mngr.getMngrTyCode().name())
                .authorities(SrvcAuthority.MNGM.name())
                .build();
    }

    @Transactional
    public UserSession getUserSession(String id) {
        MngrEntity mngr = mngrRepository.getReferenceById(Integer.parseInt(id));

        UserSession session = switch (mngr.getMngrTyCode()) {
            case SCHUL -> new SchulMngrUserSession(
                    mngr.getMngrId(),
                    mngr.getMngrNm(),
                    schulMngrMapngRepository.findByMngrNo(mngr)
                            .stream()
                            .map((SchulMngrMapngEntity schulMngrMapngEntity) -> schulMngrMapngEntity.getSchulNo().getId())
                            .findFirst()
                            .orElse(null)
            );
            case SYSTEM -> new SystemMngrUserSession(mngr.getMngrId(), mngr.getMngrNm());
        };

        mngrRepository.updateLastLoginDt(mngr, Instant.now());

        return session;
    }

}
