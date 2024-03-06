package com.daqem.jobsplustools.item.replacer.result;

public class ReplaceableResult {

    private final boolean shouldBreak;
    private final boolean shouldPlace;

    public ReplaceableResult(boolean shouldBreak, boolean shouldPlace) {
        this.shouldBreak = shouldBreak;
        this.shouldPlace = shouldPlace;
    }

    public boolean shouldBreak() {
        return shouldBreak;
    }

    public boolean shouldPlace() {
        return shouldPlace;
    }

    public static ReplaceableResult onlyBreak() {
        return new ReplaceableResult(true, false);
    }

    public static ReplaceableResult onlyPlace() {
        return new ReplaceableResult(false, true);
    }

    public static ReplaceableResult breakAndPlace() {
        return new ReplaceableResult(true, true);
    }

    public static ReplaceableResult none() {
        return new ReplaceableResult(false, false);
    }
}
