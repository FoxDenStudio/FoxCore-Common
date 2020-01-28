package net.foxdenstudio.foxcore.platform.text;

public interface Text extends TextRepresentable{

    String toPlain();

    interface Builder {

        Builder append(Text... children);

        Text build();
    }

    @Override
    default Text toText() {
        return this;
    }
}
