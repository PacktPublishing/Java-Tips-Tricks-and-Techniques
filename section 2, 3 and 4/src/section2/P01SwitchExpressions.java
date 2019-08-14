package section2;

public class P01SwitchExpressions {

    public static void main(String[] args) {
        new P01SwitchExpressions().parsePgn();
    }

    private void parsePgn() {
        String pgn = "1. e2e4 d7d5 2. e4e5 e7e6 3. d2d4 b7b6 4. Bf1b5+ c7c6 5. Bb5a4 b6b5 " +
                "6. Ba4b3 a7a6 7. Ng1e2 f7f6 8. Bc1f4 Nb8d7 9. Nb1d2 h7h6 10. Nd2f3 g7g5";
        String[] moves = pgn.split(" ");

        System.out.println("Numbered moves:");
        for (int i = 0; i < moves.length; i++) {
            String part = moves[i];
            String msg = switch (i % 3) {
                case 0:
                    break part;

                case 1:
                    break "White " + figure(part) + move(part);

                default:
                    break "Black " + figure(part) + move(part);
            } ;
            System.out.println(msg);
        }
    }

    private String figure(String part) {
        return switch (part.charAt(0)) {
            case 'N'->"Knight";
            case 'B'->"Bishop";
            case 'R'->"Rook";
            case 'Q'->"Queen";
            case 'K'->"King";
            case 'P'->"Pawn";
            default ->"Pawn";
        } ;
    }

    private String move(String part) {
        char c = part.charAt(0);
        int x = switch (c) {
            case 'N','B', 'R', 'Q', 'K', 'P'->1;
            case 'a','b', 'c', 'd', 'e', 'f', 'g', 'h'->0;
            default ->{
                String exceptionMsg = "Not expected char: " + c;
                throw new IllegalArgumentException(exceptionMsg);
            }
        } ;
        return " moves from " + part.substring(x, x + 2) +
                " to " + part.substring(x + 2, x + 4);
    }
}

