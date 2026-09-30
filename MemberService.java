package com.library.service;

import com.library.dao.MemberDAO;
import com.library.model.Member;
import java.sql.SQLException;
import java.util.List;

public class MemberService {
    private final MemberDAO dao = new MemberDAO();

    public void add(Member member) throws SQLException { dao.add(member); }
    public List<Member> findAll() throws SQLException { return dao.findAll(); }
}
