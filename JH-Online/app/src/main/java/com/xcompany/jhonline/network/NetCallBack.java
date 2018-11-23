package com.xcompany.jhonline.network;

import java.util.List;

public interface NetCallBack<T> {
    void done(List<T> list, int count, Exception e);

}
