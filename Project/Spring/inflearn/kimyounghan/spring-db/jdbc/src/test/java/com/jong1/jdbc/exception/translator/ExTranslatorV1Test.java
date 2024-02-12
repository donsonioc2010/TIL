package com.jong1.jdbc.exception.translator;

import static com.jong1.jdbc.connection.ConnectionConst.PASSWORD;
import static com.jong1.jdbc.connection.ConnectionConst.URL;
import static com.jong1.jdbc.connection.ConnectionConst.USERNAME;
import com.jong1.jdbc.connection.ConnectionConst;
import com.jong1.jdbc.domain.Member;
import com.jong1.jdbc.repository.ex.MyDbException;
import com.jong1.jdbc.repository.ex.MyDuplicateKeyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.test.context.jdbc.Sql;

@Slf4j
@SpringBootTest
@Sql(scripts = {"classpath:sql/schema.sql", "classpath:sql/data.sql"})
public class ExTranslatorV1Test {

    Repository repository;
    Service service;

    @BeforeEach
    void beforeEach() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        this.repository = new Repository(dataSource);
        this.service = new Service(this.repository);
    }

    @Test
    void duplicateKeySave() {
        this.service.create("myId");
        this.service.create("myId");
    }

    @Slf4j
    @RequiredArgsConstructor
    static class Service {

        private final Repository repository;

        public void create(String memberId) {
            try {
                repository.save(new Member(memberId, 0));
                log.info("saveId >>> {} ", memberId);
            }
            catch (MyDuplicateKeyException e) {
                log.info("키 중복, 복구 시도");
                String retryId = generateNewId(memberId);
                log.info("retryId >>> {}", retryId);
                repository.save(new Member(retryId, 0));
            }
            catch (MyDbException e) {
                log.info("DB 에러, 복구 불가");
                throw e;
            }
        }

        private String generateNewId(String memberId) {
            return memberId + new Random().nextInt(10000);
        }
    }

    @RequiredArgsConstructor
    static class Repository {

        private final DataSource dataSource;

        public Member save(Member member) {
            String sql = "insert into member(member_id, money) values(?, ?)";
            Connection conn = null;
            PreparedStatement psmt = null;
            try {
                conn = dataSource.getConnection();
                psmt = conn.prepareStatement(sql);
                psmt.setString(1, member.getMemberId());
                psmt.setInt(2, member.getMoney());
                psmt.executeUpdate();
                return member;
            }
            catch (SQLException e) {
                if (e.getErrorCode() == 23505) {
                    // 23505는 h2에서 발생하는 duplicate ErrorCode
                    throw new MyDuplicateKeyException(e);
                }
                throw new MyDbException(e);
            }
            finally {
                JdbcUtils.closeStatement(psmt);
                JdbcUtils.closeConnection(conn);
            }
        }
    }

}
