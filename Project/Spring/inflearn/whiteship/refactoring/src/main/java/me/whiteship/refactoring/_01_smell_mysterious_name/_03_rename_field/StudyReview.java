package me.whiteship.refactoring._01_smell_mysterious_name._03_rename_field;

/**
 * Reviewer, Review를 담는 클래스
 * @Data를 다해준다 생각하면 편함
 * @param reviewer
 * @param review
 */
public record StudyReview(String reviewer, String review) {

}
