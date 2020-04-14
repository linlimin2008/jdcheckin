package com.zerocm.jdcheckin.service;

import com.zerocm.jdcheckin.mapper.JdCookieMapper;
import com.zerocm.jdcheckin.pojo.domain.JdCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JdCookieService {
    @Autowired
    private JdCookieMapper jdCookieMapper;

    public List<JdCookie> getAllCoookieUser(){
        return jdCookieMapper.selectAllCookie();
    }

    public Integer insertJdCookie(JdCookie jdCookie){
        return jdCookieMapper.insert(jdCookie);
    }
}
