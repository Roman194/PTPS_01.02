import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);//ввод данных
        int tricksCount = sc.nextInt();
        sc.nextLine();
        ArrayList<String> cardPacks = new ArrayList<>();
        for(int i = 0; i < tricksCount; i++){
            cardPacks.add(sc.nextLine());
        }
        System.out.println(cardPacks);
        ArrayList<Integer> usedTricks = new ArrayList<>();
        while(true){
            try{
                int currentTrick = Integer.parseInt(sc.nextLine());
                usedTricks.add(currentTrick);
            }catch (java.lang.NumberFormatException e){
                break;
            }
        }
        System.out.println(usedTricks);

        ArrayList<ArrayList<Focus>> tricksFocuses = new ArrayList<>();//поиск перестановок, входящих в каждую комбинацию
        for(String cardPack: cardPacks){
            String [] splittedCardPack = cardPack.split(" ");
            ArrayList<Focus> trickFocuses = new ArrayList<>();
            for(int i = 0; i < splittedCardPack.length; i++){
                if(Integer.parseInt(splittedCardPack[i]) != i + 1 && !trickFocuses.contains(new Focus(i + 1, Integer.parseInt(splittedCardPack[i])))){
                    trickFocuses.add(new Focus(Integer.parseInt(splittedCardPack[i]), i + 1 ));
                }
            }
            tricksFocuses.add(trickFocuses);
        }

        System.out.println(tricksFocuses);

        Integer [] resultCardPack = new Integer[52]; //создание исходной колоды карт

        for(int i = 0 ; i < 52; i++){
            resultCardPack[i] = i + 1;
        }

        System.out.println(Arrays.toString(resultCardPack));

        for(Integer currentTrick: usedTricks){ // перестановка карт в колоде согласно использованным комбинациям
            for(Focus trickFocuses : tricksFocuses.get(currentTrick - 1)){
                int card = resultCardPack[trickFocuses.spaceOrder() - 1];
                resultCardPack[trickFocuses.spaceOrder() - 1] = resultCardPack[trickFocuses.cardOrder() - 1];
                resultCardPack[trickFocuses.cardOrder() - 1] = card;
            }
        }

        System.out.println(Arrays.toString(resultCardPack));

        ArrayList<String> resultCardNamePack = convertCardPackFromNumbersToNames(resultCardPack); //создание списка с именами карт на основе номеров изменённой колоды

        for(String currCardName: resultCardNamePack){ //вывод имён всех карт из получившейся колоды
            System.out.println(currCardName);
        }



    }

    private static ArrayList<String> convertCardPackFromNumbersToNames(Integer[] resultCardPack) {
        String [] cardNumber = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        ArrayList<String> resultCardNamePack = new ArrayList<>();

        for(int i = 0; i < resultCardPack.length; i++){
            if(resultCardPack[i] < 14){
                resultCardNamePack.add(cardNumber[resultCardPack[i] - 1]+" of Clubs");
            }else{
                if(resultCardPack[i] < 27){
                    resultCardNamePack.add(cardNumber[resultCardPack[i] - 14]+" of Diamonds");
                }else{
                    if(resultCardPack[i] < 40){
                        resultCardNamePack.add(cardNumber[resultCardPack[i] - 27]+" of Hearts");
                    }else{
                        resultCardNamePack.add(cardNumber[resultCardPack[i] - 40]+" of Spades");
                    }
                }
            }
        }
        return resultCardNamePack;
    }
}