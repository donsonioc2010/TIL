package jong1.member;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    public final JdbcTemplate template;

    public MemberRepository(JdbcTemplate template) {
        this.template = template;
    }

    // 보통 여기서 하지는 않지.., 예제용
    public void initTable() {
        template.execute("CREATE TABLE member (member_id varchar PRIMARY KEY, name VARCHAR)");
    }

    public void save(Member member) {
        template.update(
            "INSERT INTO member (member_id, name) VALUES (?, ?)",
            member.getMemberId(),
            member.getName()
        );
    }

    public Member find(String memberId) {
        return template.queryForObject(
            "SELECT member_id, name FROM member where member_id=?",
            BeanPropertyRowMapper.newInstance(Member.class),
            memberId
        );
    }

    public List<Member> findAll() {
        return template.query(
            "SELECT member_id, name FROM member",
            BeanPropertyRowMapper.newInstance(Member.class)
        );
    }
}
