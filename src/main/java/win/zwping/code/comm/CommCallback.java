package win.zwping.code.comm;

import androidx.annotation.Nullable;

public interface CommCallback<T> {

    void callback(@Nullable T t);
}
