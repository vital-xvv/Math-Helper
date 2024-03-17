package io.vital.mathematicalhelper.repository;

import io.vital.mathematicalhelper.model.Root;

public interface RootRepository {
    Long createRoot(double value);
    Root findRootByValue(double value);
}
