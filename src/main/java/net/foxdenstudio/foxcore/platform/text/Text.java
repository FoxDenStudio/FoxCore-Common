package net.foxdenstudio.foxcore.platform.text;

public interface Text {

    String toPlain();

    interface Builder {

        Builder append(Text... children);

        Text build();
    }
}
