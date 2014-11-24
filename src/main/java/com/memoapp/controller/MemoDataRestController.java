package com.memoapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.memoapp.domain.MemoData;
import com.memoapp.service.MemoDataService;

@RestController
@RequestMapping("api/memoapp")
public class MemoDataRestController {
    @Autowired
    MemoDataService memoDataService;

    @RequestMapping(method = RequestMethod.GET)
    List<MemoData> getMemoDataALL() {
        return memoDataService.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    MemoData getMemoDataOne(@PathVariable("id") Long id) {
        return memoDataService.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    MemoData postMemoData(@RequestBody MemoData memodata) {
        return memoDataService.save(memodata);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    MemoData putMemoData(@RequestBody MemoData memodata) {
        return memoDataService.save(memodata);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMemoData(@PathVariable("id") Long id) {
        memoDataService.delete(id);
    }
}
