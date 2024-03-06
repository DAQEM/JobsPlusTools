package com.daqem.jobsplustools.item.mode.breaker.multi;

import com.daqem.jobsplustools.item.mode.IMode;

public interface MultiBlockBreakerMode extends IMode {
    int getRangeX();
    int getRangeY();
    int getRangeZ();
}
