package com.lyw.rubaninit;

/**
 * Created on  2021/3/27
 * Created by  lyw
 * Created for
 */
class Test {

    private TestInterior test1 = new TestInterior();

    private String str = "werwer";
    private int cont = 2;

    static class TestInterior extends TestInterior1 {
        private static void refresh() {
            refresh1();
        }
    }

    static class TestInterior1 {
        static void refresh1() {
        }
    }
}
