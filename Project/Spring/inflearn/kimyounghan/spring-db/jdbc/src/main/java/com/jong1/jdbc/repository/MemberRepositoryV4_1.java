package com.jong1.jdbc.repository;

import com.jong1.jdbc.domain.Member;
import com.jong1.jdbc.repository.ex.MyDbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

/**
 * 예외 누수문 제 해결
 * 체크 예외를 언체크예외로 변경
 * MemberRepository 인터페이스 사용
 * Throws SQLEXception제거
 */

@Slf4j
public class MemberRepositoryV4_1 implements MemberRepository {

    private final DataSource dataSource;

    public MemberRepositoryV4_1(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member)  {
        String sql = "insert into member(member_id, money) values(?, ?)";

        Connection conn = null;
        PreparedStatement psmt = null;
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, member.getMemberId());
            psmt.setInt(2, member.getMoney());
            psmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("DB Save Error", e);
            throw new MyDbException(e);
        } finally {
            close(conn, psmt, null);
        }
    }

    @Override
    public Member findById(String memberId) {
        String sql = "select * from member where member_id = ?";
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);

            psmt.setString(1, memberId);
            rs = psmt.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("Member Not Found MemberId >>> " + memberId);
            }

        } catch (SQLException e) {
            log.error("DB Save Error", e);
            throw new MyDbException(e);
        } finally {
            close(conn, psmt, rs);
        }
    }

    @Override
    public void update(String memberId, int money) {
        String sql = "update member set money = ? where member_id = ?";

        Connection conn = null;
        PreparedStatement psmt = null;

        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);

            psmt.setInt(1, money);
            psmt.setString(2, memberId);
            int resultSize = psmt.executeUpdate();
            log.info("Update Result Size >>> {}", resultSize);
        } catch (SQLException e) {
            log.error("DB Update Error", e);
            throw new MyDbException(e);
        } finally {
            close(conn, psmt, null);
        }
    }

    @Override
    public void delete(String memberId)  {
        String sql = "delete from member where member_id = ?";

        Connection conn = null;
        PreparedStatement psmt = null;

        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);

            psmt.setString(1, memberId);
            int resultSize = psmt.executeUpdate();
            log.info("Delete Result Size >>> {}", resultSize);
        } catch (SQLException e) {
            log.error("DB Delete Error", e);
            throw new MyDbException(e);
        } finally {
            close(conn, psmt, null);
        }
    }

    private void close(Connection conn, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        DataSourceUtils.releaseConnection(conn, dataSource);
    }

    private Connection getConnection() throws SQLException {
        // 트랜잭셔 동기화를 사용하려면 DataSourceUtils를 사용해야 한다.
        Connection conn = DataSourceUtils.getConnection(dataSource);
        log.info("Get Connection >>> {}, Class >>> {}", conn, conn.getClass());
        return conn;
    }
}
