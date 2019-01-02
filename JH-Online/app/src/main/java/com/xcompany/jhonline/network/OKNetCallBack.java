package com.xcompany.jhonline.network;

import java.util.List;

public interface OKNetCallBack<T> {
    void done(T t, Exception e);

}
