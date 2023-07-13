package com.app.kms.reactboard.api.dto;

import com.app.kms.reactboard.api.entity.MemberEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor //기본생성자 자동 만듬
@AllArgsConstructor
@ToString
public class MemberDTO { //회원정보에 필요한 부분을 필드에 정의
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public static  MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        return memberDTO;
    }
}