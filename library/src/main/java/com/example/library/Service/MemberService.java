package com.example.library.Service;

import com.example.library.entity.Member;
import com.example.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    // Constructor injection for the repository
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // Retrieve all members from the database
    public List<Member> getAllMember() {
        return memberRepository.findAll();
    }

    // Retrieve a member by their ID
    public Optional<Member> getById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // Create a new member
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    // Delete a member by their ID
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    // Update an existing member
    public Member updateMember(Long memberId, Member memberDetails) {
        // Fetch the member by ID
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        // Update the member details
        member.setName(memberDetails.getName());
        member.setMembershipNumber(memberDetails.getMembershipNumber());
        member.setBooks(memberDetails.getBooks());


        return memberRepository.save(member);
    }
}
