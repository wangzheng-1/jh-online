package com.xcompany.jhonline.model.home;

import java.util.List;

/**
 * Created by xieliang on 2018/11/22 22:35
 */
public class MusicHotKey {

    /**
     * hotkey : [{"k":"说爱你 ","n":447768},{"k":"周杰伦 ","n":366048},{"k":"再见青春 ","n":314053},{"k":"东西 ","n":280437},{"k":"一如年少模样 ","n":262854},{"k":"附送折磨 ","n":207265},{"k":"悲伤逆流成河 ","n":179371},{"k":"为你我受冷风吹 ","n":172514},{"k":"陈奕迅 ","n":168055},{"k":"夜之光 ","n":155330},{"k":"我们 ","n":148608},{"k":"汪苏泷 ","n":145908},{"k":"请先说你好 ","n":138143},{"k":"你一定要幸福 ","n":131000},{"k":"激光雨 ","n":125936},{"k":"爱情有时很残忍 ","n":117099},{"k":"张韶涵 ","n":106087},{"k":"太早 ","n":102961},{"k":"一言难尽 ","n":91067},{"k":"高圆圆 ","n":84851},{"k":"一曲相思 ","n":83720},{"k":"说爱你刘至佳 ","n":80472},{"k":"怎么说我不爱你 ","n":79718},{"k":"可不可以给我你的微信 ","n":75433},{"k":"红尘来去一场梦 ","n":74712},{"k":"太多 ","n":73589},{"k":"A-LIN ","n":68026},{"k":"淋雨一直走 ","n":67068},{"k":"无名之辈 ","n":62637},{"k":"月下情缘 ","n":62343}]
     * special_key : 蒙面唱将猜猜猜
     * special_url : https://y.qq.com/m/act/maskessinger3/index.html?ADTAG=sousuoyunyingci
     */

    private String special_key;
    private String special_url;
    private List<HotkeyBean> hotkey;

    public String getSpecial_key() {
        return special_key;
    }

    public void setSpecial_key(String special_key) {
        this.special_key = special_key;
    }

    public String getSpecial_url() {
        return special_url;
    }

    public void setSpecial_url(String special_url) {
        this.special_url = special_url;
    }

    public List<HotkeyBean> getHotkey() {
        return hotkey;
    }

    public void setHotkey(List<HotkeyBean> hotkey) {
        this.hotkey = hotkey;
    }

    public static class HotkeyBean {
        /**
         * k : 说爱你
         * n : 447768
         */

        private String k;
        private int n;

        public String getK() {
            return k;
        }

        public void setK(String k) {
            this.k = k;
        }

        public int getN() {
            return n;
        }

        public void setN(int n) {
            this.n = n;
        }
    }

    @Override
    public String toString() {
        return "MusicHotKey{" +
                "special_key='" + special_key + '\'' +
                ", special_url='" + special_url + '\'' +
                ", hotkey=" + hotkey +
                '}';
    }
}
