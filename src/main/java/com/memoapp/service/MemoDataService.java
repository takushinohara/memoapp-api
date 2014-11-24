package com.memoapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memoapp.domain.MemoData;
import com.memoapp.repository.MemoDataRepository;

@Service
@Transactional
public class MemoDataService {
    @Autowired
    MemoDataRepository memoDataRepository;

    public List<MemoData> findAll() {
        return memoDataRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
    }

    public MemoData findOne(Long id) {
        return memoDataRepository.findOne(id);
    }

    public MemoData save(MemoData memodata) {
        return memoDataRepository.save(memodata);
    }

    public void delete(Long id) {
        memoDataRepository.delete(id);
    }
}
