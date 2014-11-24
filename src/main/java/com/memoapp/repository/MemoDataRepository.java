package com.memoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.memoapp.domain.MemoData;

public interface MemoDataRepository extends JpaRepository<MemoData, Long> {

}
