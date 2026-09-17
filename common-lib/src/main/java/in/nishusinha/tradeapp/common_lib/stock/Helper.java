package in.nishusinha.tradeapp.common_lib.stock;

public class Helper {
    public static boolean isSymbolSyntheticStock(String symbol) {
        return symbol.toUpperCase().startsWith("SYNTH_");
    }
}
