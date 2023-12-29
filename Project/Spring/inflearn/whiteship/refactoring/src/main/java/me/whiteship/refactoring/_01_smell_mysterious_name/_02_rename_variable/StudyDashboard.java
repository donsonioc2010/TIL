package me.whiteship.refactoring._01_smell_mysterious_name._02_rename_variable;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueComment;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 리팩토링 2 - 변수 이름 변경하기 (Rename Variable)
 */
public class StudyDashboard {

    private Set<String> reviewers = new HashSet<>();

    private Set<String> reviews = new HashSet<>();

    /**
     * 스터디 리뷰 이슈에 작성되어 있는 리뷰어 목록과 리뷰를 읽어옵니다.
     * @throws IOException
     */
    private void loadReviews() throws IOException {
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("whiteship/live-study");
        GHIssue issue = repository.getIssue(30);

        // comments -> reviews
        List<GHIssueComment> reviews = issue.getComments();
        for (GHIssueComment review : reviews) {
            reviewers.add(review.getUserName());
            this.reviews.add(review.getBody());
        }
    }

    public Set<String> getReviewers() {
        return reviewers;
    }

    public Set<String> getReviews() {
        return reviews;
    }

    public static void main(String[] args) throws IOException {
        StudyDashboard studyDashboard = new StudyDashboard();
        studyDashboard.loadReviews();
        studyDashboard.getReviewers().forEach(System.out::println);
        studyDashboard.getReviews().forEach(System.out::println);
    }
}
