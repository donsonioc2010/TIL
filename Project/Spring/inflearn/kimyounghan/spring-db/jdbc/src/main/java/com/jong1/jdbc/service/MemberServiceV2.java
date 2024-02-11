package com.jong1.jdbc.service;

import com.jong1.jdbc.domain.Member;
import com.jong1.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Transaction을 활용
 */
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final MemberRepositoryV2 memberRepository;
    private final DataSource dataSource;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection conn = dataSource.getConnection();
        try{
            conn.setAutoCommit(false);
            businessLogic(conn, fromId, toId, money);
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw new IllegalStateException(e);
        } finally {
            conn.setAutoCommit(true);
            JdbcUtils.closeConnection(conn);
        }
    }

    private void businessLogic(Connection conn, String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(conn, fromId);
        Member toMember = memberRepository.findById(conn, toId);

        memberRepository.update(conn, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(conn, toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이채중 예외 발생");
        }
    }

}
