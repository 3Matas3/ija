package ija.projekt.figures;

public enum FigureType {

    /**
     *
     */
    KING("k"),

    /**
     *
     */
    KNIGHT("n"),

    /**
     *
     */
    ROOK("r"),

    /**
     *
     */
    QUEEN("q"),

    /**
     *
     */
    BISHOP("b"),

    /**
     *
     */
    PAWN("p");

    private final String value;

    private FigureType(String value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    public String toStringType() {
        return this.value;
    }

    /**
     *
     * @param value
     * @return
     */
    public static FigureType fromString(String value) {
        FigureType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            FigureType figure = var1[var3];
            if (figure.value.equalsIgnoreCase(value)) {
                return figure;
            }
        }

        return null;
    }
}
