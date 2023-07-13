package com.app.kms.reactboard.api.service;

import com.app.kms.reactboard.api.dto.MemberDTO;
import com.app.kms.reactboard.api.entity.MemberEntity;
import com.app.kms.reactboard.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service //Spring이 관리해주는 객체 , Spring Bean으로 등록
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
        // 1. dto -> entity 변환
        // 2. repository의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO); //alt Enter 좌변 만들어줌
        memberRepository.save(memberEntity); //save 는 JPA가 만들어줌 save호출로 Spring DATA JPA가 Insert 쿼리 만들어서 Table에 추가
        // repository의 save메서드 호출 (조건. entity객체를 넘겨줘야 함)

    }

    public MemberDTO login(MemberDTO memberDTO){
        /**
         * 1.회원이 입력한 이메일로 DB에서 조회
         * 2.DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(byMemberEmail.isPresent()) {
            // 조회 결과가 있는 경우 (해당 이메일을 가진 회원 정보가 있음)
            MemberEntity memberEntity = byMemberEmail.get();
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                // 비밀번호 일치
                // entity -> dto 객체 변환 후 리턴 : Entity 1개 - DTO 1개
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                // 비밀번호 불일치
                return null;
            }
        } else {
            //조회 결과가 없다 (해당 이메일을 가진 회원이 없다)
            return null;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();//repository 제공 method
        List<MemberDTO> memberDTOList = new ArrayList<>();
        //Entity 여러개 담긴 List객체를 DTO가 여러개 담긴 List객체로 옮겨 답는다. 위처럼 그냥 대입 안됩니다.
        for (MemberEntity memberEntity: memberEntityList){
            //MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
            //memberDTOList.add(memberDTO); 이 두줄 = 아래 한줄
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);//1.alt Enter 자동완성, 2.한개 받을 때 Optional로 감싸준다.
        if(optionalMemberEntity.isPresent()){
            //1.optional 객체를 한번 꺼내야 Entity객체가 보임 2.그것을 DTO에 toMemberDTO 메서드로 보냄 그 결과를 Controller로 리턴
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            return memberDTO;
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        //save 메서드는 id가 없으면 insert 있으면 update 쿼리 날려 줍니다.
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if (byMemberEmail.isPresent()){
            //조회 결과가 있다 -> 사용할 수 없다.
            return null;
        } else {
            //없다 -> 사용 가능
            return "ok";
        }
    }
}
