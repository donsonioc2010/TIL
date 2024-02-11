package com.jong1.jdbc.repository;

import com.jong1.jdbc.connection.DBConnectionUtil;
import com.jong1.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * JDBC - DriverManager를 이용한 DB 연동
 */

@Slf4j
public class MemberRepositoryV0 {
    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values(?, ?)";

        try (
            Connection conn = getConnection();
            PreparedStatement psmt = conn.prepareStatement(sql);
        ) {
            psmt.setString(1, member.getMemberId());
            psmt.setInt(2, member.getMoney());
            psmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("DB Save Error", e);
            throw e;
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";
        try (
            Connection conn = getConnection();
            PreparedStatement psmt = conn.prepareStatement(sql);
        ) {
            psmt.setString(1, memberId);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    Member member = new Member();
                    member.setMemberId(rs.getString("member_id"));
                    member.setMoney(rs.getInt("money"));
                    return member;
                } else {
                    throw new NoSuchElementException("Member Not Found MemberId >>> " + memberId);
                }
            }
        } catch (SQLException e) {
            log.error("DB Save Error", e);
            throw e;
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where member_id = ?";
        try (
            Connection conn = getConnection();
            PreparedStatement psmt = conn.prepareStatement(sql);
        ) {
            psmt.setInt(1, money);
            psmt.setString(2, memberId);
            int resultSize = psmt.executeUpdate();
            log.info("Update Result Size >>> {}", resultSize);
        } catch (SQLException e) {
            log.error("DB Update Error", e);
            throw e;
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql =  "delete from member where member_id = ?";

        try (
            Connection conn = getConnection();
            PreparedStatement psmt = conn.prepareStatement(sql);
        ) {
            psmt.setString(1, memberId);
            int resultSize = psmt.executeUpdate();
            log.info("Delete Result Size >>> {}", resultSize);
        } catch (SQLException e) {
            log.error("DB Delete Error", e);
            throw e;
        }
    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
