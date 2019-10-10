package com.tangzy.tzymvp.test;

import com.tangzy.tzymvp.util.Logger;

/**
 * Outer class
 *
 * @author Administrator
 * @date 2019/10/9
 */
public class Outer {
    class Inner {
        public String publicString = "Inner.publicString";
    }

    Other anonymousOther = new Other() {
        public String publicString = "Anonymous Other.publicString";
    };
    public Other getAnonymousOther() {
        return anonymousOther;
    }

    Other Other = new Other();
    public Other getOther() {
        return Other;
    }

    public static void main(String args[]) {
       Logger.d("tangzy1", new Outer().new Inner().toString());
        System.out.println("\t");
        Logger.d("tangzy1", new Outer().getAnonymousOther().toString());
        System.out.println("\t");
        Logger.d("tangzy1", new Outer().getOther().toString());
    }
}

class Other {
    public String publicString = "Other.publicString";
}
