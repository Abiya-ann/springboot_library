package com.example.library.Controller;


import com.example.library.Service.MemberService;
import com.example.library.entity.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    public MemberController(MemberService memberService){
        this.memberService=memberService;
    }
    @GetMapping()
    public ResponseEntity <List<Member>>getAll(){
        List<Member> member=memberService.getAllMember();
        return ResponseEntity.ok(member);
    }
    @GetMapping("/{memberId}")
    public ResponseEntity <Member >getById(@PathVariable Long memberId){
       Optional <Member> member=memberService.getById(memberId);
       return member.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity <Void> delete(@PathVariable Long memberId){
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity <Member> saveMember(@RequestBody Member member){
        Member savedMember = memberService.saveMember(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }

    @PutMapping("/{memberId}")
    public Member updateMember(@PathVariable Long memberId, Member member){
        return memberService.updateMember(memberId, member);
    }

}
