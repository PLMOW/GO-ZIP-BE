package com.gozip.repository;

import com.gozip.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;



public interface MemberRepository extends JpaRepository <Member, Long>{
}
