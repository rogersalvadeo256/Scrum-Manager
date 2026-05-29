package com.scrummanager.business.contract;

public record ProjectMetricsData(
        String currentMonth,
        int totalTasks,
        int todoCount,
        int doingCount,
        int doneCount,
        int tasksThisMonth,
        int doneThisMonth,
        int totalPoints,
        int completedPoints,
        int velocityThisMonth,
        int totalSprints,
        int activeSprints,
        int completedSprints,
        int sprintCompletionRate,
        int taskCompletionRate,
        int membersCount) {
}
