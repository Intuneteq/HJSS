package com.hjss.exceptions;

public class GradeMisMatchException extends Exception{
    public GradeMisMatchException() {
        super("Lesson grade must match Learner's grade or one step higher");
    }
}
