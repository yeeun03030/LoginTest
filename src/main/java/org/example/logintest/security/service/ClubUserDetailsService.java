package org.example.logintest.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.logintest.entity.ClubMember;
import org.example.logintest.repository.ClubMemberRepository;
import org.example.logintest.security.dto.ClubAuthMemberDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {
    private final ClubMemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("== ClubUserDetailsService: " + username);
        Optional<ClubMember> result = repository.findByEmail(username, false);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("Check Email or social");
        }

        ClubMember clubMember = result.get();
        log.info(clubMember);

        ClubAuthMemberDTO clubAuthMemberDTO = new ClubAuthMemberDTO(
                clubMember.getEmail(),
                clubMember.getPassword(),
                clubMember.isFromSocial(),
                clubMember.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet())
        );

        clubAuthMemberDTO.setName(clubMember.getName());
//        clubAuthMemberDTO.setFromSocial(clubMember.isFromSocial());

        return clubAuthMemberDTO;
    }
}
