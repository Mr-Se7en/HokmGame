package com.example.hokmgame;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class GameActivity extends AppCompatActivity implements TrumpCaller.OnCardDistributionCompleteListener {

    private Handler handler;
    private RecyclerView recyclerViewPlayer1;
    private RecyclerView recyclerViewPlayer2;
    private RecyclerView recyclerViewPlayer3;
    private RecyclerView recyclerViewPlayer4;
    private RecyclerView userPointHolder;
    private RecyclerView oppPointHolder;
    private PointHolderAdapter userPointAdapter;
    private PointHolderAdapter oppPointAdapter;
    private List<PointItem> userPointList;
    private List<PointItem> oppPointList;
    private CardAdapter cardAdapterPlayer1;
    private CardAdapterOpVertical cardAdapterPlayer2;
    private CardAdapterOpHorizontal cardAdapterPlayer3;
    private CardAdapterOpVertical cardAdapterPlayer4;
    private DynamicHorizontalOverlappingDecoration horizontalDecoration;
    private DynamicHorizontalOverlappingDecoration horizontalDecoration2;
    private DynamicVerticalOverlappingDecoration verticalDecoration;
    private DynamicVerticalOverlappingDecoration verticalDecoration2;
    private ImageView card1, card2, card3, card4;
    private int trump=-1;
    private String trumpSuit=null;
    public static List<String> deck = new ArrayList<>();
    public List<String> cardListPlayer1 = new ArrayList<>();
    public List<String> cardListPlayer2 = new ArrayList<>();
    public List<String> cardListPlayer3 = new ArrayList<>();
    public List<String> cardListPlayer4 = new ArrayList<>();
    CardManager cardmanager;
    private static final List<String> SUIT_ORDER = Arrays.asList("h", "s", "d", "c");
    private static final List<String> RANK_ORDER = Arrays.asList("a", "k", "q", "j", "10", "9", "8", "7", "6", "5", "4", "3", "2");
    public TextView trumpText;
    public TextView player2Txt,player3Txt,player4Txt;
    public TextView pointsTxt1,pointsTxt2;
    private int round = 1;
    private int currentOrder = 1;
    private int currentPlayer = 1;
    private int group1Points = 0;
    private int group2Points = 0;
    private int group1 = 0;
    private int group2 = 0;

    class CardManager{
        private String suit=null;
        private int Winning=0;
        private final String[] cards=new String[5] ;
        public String getSuit() {
            return suit;
        }
        public void setSuit(String s){suit=s;}
        public void setWinning(int w){Winning=w;}
        public int getWinning() {
            return Winning;
        }
        public String getCards(int num){
            return cards[num];
        }

        public void setCards(String card , int p) {
            this.cards[p] = card;
        }
        public void updateWinning(int player){
            if(Winning==0) {
                Winning=player;
                return;
            }
            if(cards[this.Winning].substring(0,1).equals(cards[player].substring(0,1))){
                if((RANK_ORDER.indexOf(cards[this.Winning].substring(1)))>(RANK_ORDER.indexOf(cards[player].substring(1)))){
                    Winning=player;
                }
            }
            else if (cards[player].substring(0,1).equals(trumpSuit)){
                Winning=player;
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handler = new Handler(Looper.getMainLooper());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        trumpText=findViewById(R.id.trumpText);

//        userPointHolder = findViewById(R.id.user_point_holder);
//        userPointHolder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        oppPointHolder = findViewById(R.id.opponent_point_holder);
//        oppPointHolder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//
//        userPointList=new ArrayList<>();
//        oppPointList=new ArrayList<>();
//
//        userPointList.add(new PointItem(1));
//        userPointList.add(new PointItem(2));
//        userPointList.add(new PointItem(3));
//        userPointList.add(new PointItem(4));
//        userPointList.add(new PointItem(5));
//        userPointList.add(new PointItem(6));
//        userPointList.add(new PointItem(7));
//        userPointList.add(new PointItem(8));
//
//        oppPointList.add(new PointItem(1));
//        oppPointList.add(new PointItem(2));
//        oppPointList.add(new PointItem(3));
//        oppPointList.add(new PointItem(4));
//        oppPointList.add(new PointItem(5));
//        oppPointList.add(new PointItem(6));
//        oppPointList.add(new PointItem(7));
//        oppPointList.add(new PointItem(8));
//
//        userPointAdapter = new PointHolderAdapter(userPointList,false);
//        userPointHolder.setAdapter(userPointAdapter);
//
//        oppPointAdapter = new PointHolderAdapter(oppPointList,true);
//        oppPointHolder.setAdapter(oppPointAdapter);

        cardmanager=new CardManager();

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);

        player2Txt = findViewById(R.id.player_txt2);
        player3Txt = findViewById(R.id.player_txt3);
        player4Txt = findViewById(R.id.player_txt4);

        pointsTxt1 = findViewById(R.id.user_points);
        pointsTxt2 = findViewById(R.id.opponent_points);

        // Initialize RecyclerViews
        recyclerViewPlayer1 = findViewById(R.id.player1_hand_recyclerview);
        recyclerViewPlayer1.setEnabled(false);
        recyclerViewPlayer2 = findViewById(R.id.player2_hand_recyclerview);
        recyclerViewPlayer3 = findViewById(R.id.player3_hand_recyclerview);
        recyclerViewPlayer4 = findViewById(R.id.player4_hand_recyclerview);

        // Set LayoutManagers
        recyclerViewPlayer1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewPlayer2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewPlayer3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewPlayer4.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Initialize Card Lists for each player


//        String[] suits = {"h", "d", "c", "s"};
//        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "j", "q", "k", "a"};
//
//// Populate the card lists with the standard deck of cards
//        for (String suit : suits) {
//            for (String rank : ranks) {
//                String card = suit+rank;
//                deck.add(card);
//            }
//        }
//
//        Collections.shuffle(deck);

        // Initialize CardAdapters for each player
        cardAdapterPlayer1 = new CardAdapter(cardListPlayer1, new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                onItemClicked(position);
            }
        });
        cardAdapterPlayer2 = new CardAdapterOpVertical(this, cardListPlayer2);
        cardAdapterPlayer3 = new CardAdapterOpHorizontal(this, cardListPlayer3);
        cardAdapterPlayer4 = new CardAdapterOpVertical(this, cardListPlayer4);

        // Set Adapters to RecyclerViews
        recyclerViewPlayer1.setAdapter(cardAdapterPlayer1);
        recyclerViewPlayer2.setAdapter(cardAdapterPlayer2);
        recyclerViewPlayer3.setAdapter(cardAdapterPlayer3);
        recyclerViewPlayer4.setAdapter(cardAdapterPlayer4);

        // Initialize Decorations
        horizontalDecoration = new DynamicHorizontalOverlappingDecoration();
        horizontalDecoration2 = new DynamicHorizontalOverlappingDecoration();

        verticalDecoration = new DynamicVerticalOverlappingDecoration();
        verticalDecoration2 = new DynamicVerticalOverlappingDecoration();

        // Add ItemDecorations
        recyclerViewPlayer1.addItemDecoration(horizontalDecoration);
        recyclerViewPlayer2.addItemDecoration(verticalDecoration);
        recyclerViewPlayer3.addItemDecoration(horizontalDecoration2);
        recyclerViewPlayer4.addItemDecoration(verticalDecoration2);

        // Calculate and set overlaps
        calculateAndSetOverlaps();


        startGame();
    }

    private void startGame(){
        if(round >=7){
            if(group2 ==7|| group1 ==7){
                endGame();
                return;
            }
        }
            if(trump!=-1) trump=currentPlayer;
            String[] suits = {"h", "d", "c", "s"};
            String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "j", "q", "k", "a"};

// Populate the card lists with the standard deck of cards
            for (String suit : suits) {
                for (String rank : ranks) {
                    String card = suit+rank;
                    deck.add(card);
                }
            }

            Collections.shuffle(deck);
            addFragment(new TrumpCaller(trump));

    }
    private void startRound(){
        if(currentOrder== 5){
            endRound();
//            handler.postDelayed(() ->endRound(), 500);

            if(group2Points ==7|| group1Points ==7){
                round++;
                if (group1Points > group2Points) {
                    group1++;
                } else {
                    group2++;
                }
                group1Points=0;
                group2Points=0;
                cardListPlayer1.clear();
                cardListPlayer2.clear();
                cardListPlayer3.clear();
                cardListPlayer4.clear();
                cardAdapterPlayer1.notifyDataSetChanged();
                cardAdapterPlayer2.notifyDataSetChanged();
                cardAdapterPlayer3.notifyDataSetChanged();
                cardAdapterPlayer4.notifyDataSetChanged();
                player4Txt.setText(getString(R.string.player4));
                player3Txt.setText(getString(R.string.player3));
                player2Txt.setText(getString(R.string.player2));
                String p=getString(R.string.our_points)+group1Points;
                pointsTxt1.setText(p);
                p=getString(R.string.their_points)+group2Points;
                pointsTxt2.setText(p);
                //todo the between round msg
                startGame();
                return;
            }
        }
        handler.postDelayed(() ->playTurn(), 1350);
    }
    private void playTurn(){
        if(currentPlayer==1){
            recyclerViewPlayer1.setEnabled(true);
        }
        else {
           recyclerViewPlayer1.setEnabled(false);
           String card=playCard(currentOrder,currentPlayer);
            int position;
            switch (currentPlayer){
                case 2:
                    position=cardListPlayer2.indexOf(card);
                    break;
                case 3:
                    position=cardListPlayer3.indexOf(card);
                    break;
                case 4:
                    position=cardListPlayer4.indexOf(card);
                    break;
                default: position=0;
            }
           cardmanager.setCards(card,currentPlayer);
//           removeCardFromPlayer(currentPlayer,card);
//           int cardResId = getResources().getIdentifier(card, "drawable", getPackageName());
//           switch (currentPlayer) {
//               case 2:
//                   card2.setImageResource(cardResId);
//                   break;
//               case 3:
//                   card3.setImageResource(cardResId);
//                   break;
//               case 4:
//                   card4.setImageResource(cardResId);
//                   break;
//           }
            if (currentOrder==1) {
                cardmanager.setSuit(card.substring(0, 1));
            }
//            playCardWithAnimation2(card,position,currentPlayer);
            handler.postDelayed(() ->playCardWithAnimation2(card,position,currentPlayer), 500);
        }
//        cardmanager.updateWinning(currentPlayer);
//        currentPlayer=(currentPlayer+1)%4;
//        if(currentPlayer==0) currentPlayer=4;
//        currentOrder++;
//        startRound();
    }
    private void endRound(){
        currentOrder=1;
        int winner=cardmanager.getWinning();
        currentPlayer=winner;
        cardmanager.setWinning(0);
        String points;
        if(winner==1|winner==3){
            group1Points++;
            points=getString(R.string.our_points)+ group1Points;
            pointsTxt1.setText(points);
        }
        else{
            group2Points++;
            points=getString(R.string.their_points)+ group2Points;
            pointsTxt2.setText(points);
        }
        handler.postDelayed(() ->cleanCardWithAnimation(new ImageView[]{card1,card2,card3,card4},winner), 500);
    }
    private void endGame(){
        int dub=(group1 > group2)?1:2;
        showDialogAndClose(group1, group2,dub);
    }
    private void showDialogAndClose(int group1,int group2,int winner) {
        new AlertDialog.Builder(this)
                .setMessage("and the winner is group"+winner+'\n'+"group 1: "+group1+'\n'+"group 2: "+group2)
                .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Close the activity
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }
    private void calculateAndSetOverlaps() {
        calculateHorizontalOverlap(recyclerViewPlayer1, cardAdapterPlayer1, horizontalDecoration);
        horizontalDecoration.setOverlapWidth(200);
        calculateVerticalOverlap(recyclerViewPlayer2, cardAdapterPlayer2, verticalDecoration);
        horizontalDecoration2.setOverlapWidth(150);
        calculateVerticalOverlap(recyclerViewPlayer4, cardAdapterPlayer4, verticalDecoration2);
    }

    private void calculateHorizontalOverlap(RecyclerView recyclerView, CardAdapter adapter, DynamicHorizontalOverlappingDecoration horizontalDecoration) {
        int itemCount = adapter.getItemCount();
        if (itemCount > 1) {
            int totalWidth = recyclerView.getWidth();
            int cardWidth = 150;
            int overlapWidth = Math.max(0, Math.abs((totalWidth - cardWidth) / itemCount));
            horizontalDecoration.setOverlapWidth(overlapWidth);
            recyclerView.invalidateItemDecorations();
        }
    }

    private void calculateVerticalOverlap(RecyclerView recyclerView, CardAdapterOpVertical adapter, DynamicVerticalOverlappingDecoration decoration) {
            decoration.setOverlapHeight(150);
            recyclerView.invalidateItemDecorations();
    }
    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.trump_caller, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onCardDistributed(String card, int playerIndex) {
        addCard(card, playerIndex);
        // Update the RecyclerView adapter for the corresponding player
        switch (playerIndex) {
            case 0:
                cardAdapterPlayer1.notifyDataSetChanged();
                break;
            case 1:
                cardAdapterPlayer2.notifyDataSetChanged();
                break;
            case 2:
                cardAdapterPlayer3.notifyDataSetChanged();
                break;
            case 3:
                cardAdapterPlayer4.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCardDistributionComplete() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment trumpCallerFragment = fragmentManager.findFragmentById(R.id.trump_caller);
        if (trumpCallerFragment != null) {
            fragmentTransaction.remove(trumpCallerFragment).commit();
        }
        startRound();
    }

    public void addCard(String card, int p) {
        switch (p) {
            case 0:
                cardListPlayer1.add(card);
                cardListPlayer1.sort((card1, card2) -> {
                    // Extract suits and ranks
                    String suit1 = card1.substring(0, 1);
                    String suit2 = card2.substring(0, 1);
                    String rank1 = card1.substring(1);
                    String rank2 = card2.substring(1);

                    // Compare suits
                    int suitComparison = Integer.compare(SUIT_ORDER.indexOf(suit1), SUIT_ORDER.indexOf(suit2));
                    if (suitComparison != 0) {
                        return suitComparison;
                    }

                    // If suits are the same, compare ranks
                    return Integer.compare(RANK_ORDER.indexOf(rank1), RANK_ORDER.indexOf(rank2));
                });
                break;
            case 1:
                cardListPlayer2.add(card);
                cardListPlayer2.sort((card1, card2) -> {
                    // Extract suits and ranks
                    String suit1 = card1.substring(0, 1);
                    String suit2 = card2.substring(0, 1);
                    String rank1 = card1.substring(1);
                    String rank2 = card2.substring(1);

                    // Compare suits
                    int suitComparison = Integer.compare(SUIT_ORDER.indexOf(suit1), SUIT_ORDER.indexOf(suit2));
                    if (suitComparison != 0) {
                        return suitComparison;
                    }

                    // If suits are the same, compare ranks
                    return Integer.compare(RANK_ORDER.indexOf(rank1), RANK_ORDER.indexOf(rank2));
                });
                break;
            case 2:
                cardListPlayer3.add(card);
                cardListPlayer3.sort((card1, card2) -> {
                    // Extract suits and ranks
                    String suit1 = card1.substring(0, 1);
                    String suit2 = card2.substring(0, 1);
                    String rank1 = card1.substring(1);
                    String rank2 = card2.substring(1);

                    // Compare suits
                    int suitComparison = Integer.compare(SUIT_ORDER.indexOf(suit1), SUIT_ORDER.indexOf(suit2));
                    if (suitComparison != 0) {
                        return suitComparison;
                    }

                    // If suits are the same, compare ranks
                    return Integer.compare(RANK_ORDER.indexOf(rank1), RANK_ORDER.indexOf(rank2));
                });
                break;
            case 3:
                cardListPlayer4.add(card);
                cardListPlayer4.sort((card1, card2) -> {
                    // Extract suits and ranks
                    String suit1 = card1.substring(0, 1);
                    String suit2 = card2.substring(0, 1);
                    String rank1 = card1.substring(1);
                    String rank2 = card2.substring(1);

                    // Compare suits
                    int suitComparison = Integer.compare(SUIT_ORDER.indexOf(suit1), SUIT_ORDER.indexOf(suit2));
                    if (suitComparison != 0) {
                        return suitComparison;
                    }

                    // If suits are the same, compare ranks
                    return Integer.compare(RANK_ORDER.indexOf(rank1), RANK_ORDER.indexOf(rank2));
                });
                break;
            default:
                break;
        }
    }
    public void onBidComplete(int tr){
        switch (tr){
            case 1:
                trumpText.setText(R.string.trump_hearts);
                trumpSuit ="h";
                break;
            case 2:
                trumpText.setText(R.string.trump_diamonds);
                trumpSuit ="d";
                break;
            case 3:
                trumpText.setText(R.string.trump_clubs);
                trumpSuit ="c";
                break;
            case 4:
                trumpText.setText(R.string.trump_spades);
                trumpSuit ="s";
                break;
            default:
                trumpText.setText(R.string.broken);
                break;
        }
    }
    public void onTrumpApp(int tr){
        trump =tr;
        currentPlayer=tr;
        String text = "Trump: ";
        int duration = Toast.LENGTH_SHORT;
        Toast toast;
        String neoString;
        switch (trump){
            case 2:
                neoString=player2Txt.getText()+"\uD83D\uDC51";
                player2Txt.setText(neoString);
                toast = Toast.makeText(this, text+"player 2", duration);
                toast.show();
                break;
            case 3:
                neoString=player3Txt.getText()+"\uD83D\uDC51";
                player3Txt.setText(neoString);
                toast = Toast.makeText(this, text+"player 3", duration);
                toast.show();
                break;
            case 4:
                neoString=player4Txt.getText()+"\uD83D\uDC51";
                player4Txt.setText(neoString);
                toast = Toast.makeText(this, text+"player 4", duration);
                toast.show();
                break;
            default:
                toast = Toast.makeText(this, text+"You", duration);
                toast.show();
                break;
        }

    }

    public String LargestE(List<String> player){
        String max= player.get(0);
        for(int i=1;i<player.size();i++){
            if(player.get(i).substring(0, 1).equals(player.get(i - 1).substring(0, 1))){
                if ((RANK_ORDER.indexOf(player.get(i).substring(1)) <RANK_ORDER.indexOf(max.substring(1)))){
                    max=player.get(i);
                }
            }
        }
        return max;
    }
    public String LargestSuit(List<String> player, String Suit){
        String ans="";
        for(int i=0;i<player.size();i++){
            if(player.get(i).substring(0, 1).equals(Suit)){
                ans= player.get(i);
                break;
            }
        }
        return ans;
    }
    public String LowestSuit(List<String> player, String Suit){
        String ans="";
        for(int i=0;i<player.size();i++){
            if(player.get(i).substring(0, 1).equals(Suit)){
                if((i+1)<player.size()) {
                    if (!(player.get(i).substring(0, 1).equals(player.get(i + 1).substring(0, 1)))) {
                        ans = player.get(i);
                        break;
                    }
                }
                else{
                    ans = player.get(i);
                    break;
                }

            }
        }
        return ans;
    }
    public String LowestE(List<String> player){
        String min= player.get(player.size()-1);
        for(int i=player.size()-1;i>=0;i--){
            if((i-1)>0&&!player.get(i).substring(0, 1).equals(player.get(i - 1).substring(0, 1))){
                if ((RANK_ORDER.indexOf(player.get(i-1).substring(1)) >RANK_ORDER.indexOf(min.substring(1)))){
                    min=player.get(i);
                }
            }
        }
        return min;
    }
    public List<String> p2L(int player){
        switch(player){
            case 2:
                return cardListPlayer2;
            case 3:
                return cardListPlayer3;
            case 4:
                return cardListPlayer4;
        }
        return null;
    }
    public String playCard(int place,int player){
        switch (place){
            case 1:
                return LargestE(p2L(player));
            case 2:
                if(isAvailable(cardmanager.getSuit(),p2L(player))){
                    if((RANK_ORDER.indexOf(LargestSuit(p2L(player),cardmanager.getSuit()).substring(1)) <RANK_ORDER.indexOf(cardmanager.getCards(cardmanager.getWinning()).substring(1)))){
                        return LargestSuit(p2L(player),cardmanager.getSuit());
                    }
                    else {
                        return LowestSuit(p2L(player),cardmanager.getSuit());
                    }
                }
                else {
                    if(isAvailable(trumpSuit ,p2L(player))){
                        return LargestSuit(p2L(player),trumpSuit);
                    }
                    else {
                        return LowestE(p2L(player));
                    }
                }
            case 3:
                if(isAvailable(cardmanager.getSuit(),p2L(player))){
                    if((RANK_ORDER.indexOf(cardmanager.getCards(((player+2)%4)+1).substring(1)) <RANK_ORDER.indexOf(cardmanager.getCards(cardmanager.getWinning()).substring(1)))){
                        return LowestSuit(p2L(player),cardmanager.getSuit());
                    }
                    else if ((RANK_ORDER.indexOf(LargestSuit(p2L(player),cardmanager.getSuit()).substring(1)) <RANK_ORDER.indexOf(cardmanager.getCards(cardmanager.getWinning()).substring(1)))) {
                        return LargestSuit(p2L(player),cardmanager.getSuit());
                    }
                    else {
                        return LowestSuit(p2L(player),cardmanager.getSuit());
                    }

                }
                else {
                    if(isAvailable(trumpSuit ,p2L(player))){
                        if((RANK_ORDER.indexOf(cardmanager.getCards(((player+2)%4)+1).substring(1)) <RANK_ORDER.indexOf(cardmanager.getCards(cardmanager.getWinning()).substring(1)))){
                            return LowestE(p2L(player));
                        }
                        else {
                            return LargestSuit(p2L(player),trumpSuit);
                        }
                    }
                    else {
                        return LowestE(p2L(player));
                    }
                }

            case 4:
                if(isAvailable(cardmanager.getSuit(),p2L(player))){
                    if((RANK_ORDER.indexOf(cardmanager.getCards(((player+2)%4)+1).substring(1)) <RANK_ORDER.indexOf(cardmanager.getCards(cardmanager.getWinning()).substring(1)))){
                        return LowestSuit(p2L(player),cardmanager.getSuit());
                    }
                    else if ((RANK_ORDER.indexOf(LargestSuit(p2L(player),cardmanager.getSuit()).substring(1)) <RANK_ORDER.indexOf(cardmanager.getCards(cardmanager.getWinning()).substring(1)))) {
                        return LargestSuit(p2L(player),cardmanager.getSuit());
                    }
                    else {
                        return LowestSuit(p2L(player),cardmanager.getSuit());
                    }

                }
                else {
                    if(isAvailable(trumpSuit ,p2L(player))){
                        if(cardmanager.getCards(((player+2)%4)+1).substring(0,1).equals(trumpSuit)){
                            if(cardmanager.getCards(((player-1)%4)+1).substring(0,1).equals(trumpSuit)){
                                return LowestE(p2L(player));
                            }
                            else {
                                if((RANK_ORDER.indexOf(cardmanager.getCards(((player+2)%4)+1).substring(1)) <RANK_ORDER.indexOf(cardmanager.getCards(((player-1)%4)+1)))){
                                    return LowestE(p2L(player));
                                }
                                else {
                                    if((RANK_ORDER.indexOf(LargestSuit(p2L(player),trumpSuit).substring(1)) <RANK_ORDER.indexOf(cardmanager.getCards(((player-1)%4)+1)))){
                                        return LargestSuit(p2L(player),trumpSuit);
                                    }
                                    else {
                                        return LowestE(p2L(player));
                                    }
                                }
                            }
                        }
                        else{
                            if(cardmanager.getCards(((player-1)%4)+1).substring(0,1).equals(trumpSuit)){
                                if((RANK_ORDER.indexOf(LargestSuit(p2L(player),trumpSuit).substring(1)) <RANK_ORDER.indexOf(cardmanager.getCards(((player-1)%4)+1)))){
                                    return LargestSuit(p2L(player),trumpSuit);
                                }
                                else {
                                    return LowestE(p2L(player));
                                }
                            }
                            else {
                                return LowestSuit(p2L(player),trumpSuit);
                            }
                        }
                    }
                    else {
                        return LowestE(p2L(player));
                    }
                }
        }

        return null;
    }
    public boolean isAvailable(String suit, List <String> player){
        for (int i=0;i<player.size();i++){
            if(player.get(i).substring(0, 1).equals(suit)) {
                return true;
            }
        }
         return false;
    }
    private void removeCardFromPlayer(int playerIndex, String card) {
        switch (playerIndex) {
            case 2:
                cardListPlayer2.remove(card);
                cardAdapterPlayer2.removeCard(card);
                cardAdapterPlayer2.notifyDataSetChanged();
                break;
            case 3:
                cardListPlayer3.remove(card);
                cardAdapterPlayer3.removeCard(card);
                cardAdapterPlayer3.notifyDataSetChanged();
                break;
            case 4:
                cardListPlayer4.remove(card);
                cardAdapterPlayer4.removeCard(card);
                cardAdapterPlayer4.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    public void onItemClicked(int position) {
        if(!recyclerViewPlayer1.isEnabled()) return;
        String card=cardListPlayer1.get(position);
        // Check conditions here before allowing the card to be played
        if (canPlayCard(card)) {
            // Remove the card and update RecyclerView
            cardListPlayer1.remove(position);
            cardAdapterPlayer1.notifyItemRemoved(position);
            cardmanager.setCards(card,1);
            if(cardmanager.getSuit()==null) {
                cardmanager.setSuit(card.substring(0,1));
            }
            playCardWithAnimation(card, position);
        } else {
            // Show a message or handle the case where the card cannot be played
            Toast.makeText(this, "You cannot play this card.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean canPlayCard(String card) {
        if(isAvailable(cardmanager.getSuit(),cardListPlayer1)){
            return card.substring(0, 1).equals(cardmanager.getSuit());
        }
        return true;
    }

    private void playCardWithAnimation(String card, int position) {
        View cardView = recyclerViewPlayer1.getLayoutManager().findViewByPosition(position);
        if (cardView == null) return;

        // Calculate the start and end coordinates for the animation
        int[] startLocation = new int[2];
        cardView.getLocationOnScreen(startLocation);
        int[] endLocation = new int[2];
        card1.getLocationOnScreen(endLocation);

        float startX = startLocation[0];
        float startY = startLocation[1];
        float endX = endLocation[0];
        float endY = endLocation[1];

        // Create the animation
        TranslateAnimation animation = new TranslateAnimation(0, endX - startX, 0, endY - startY);
        animation.setDuration(1000);
        animation.setFillAfter(true);

        // Start the animation
        cardView.startAnimation(animation);

        // Update the card1 ImageView after the animation
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                recyclerViewPlayer1.setEnabled(false);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                // Update the card1 ImageView with the played card
                int cardResId = getResources().getIdentifier(card, "drawable", getPackageName());
                card1.setImageResource(cardResId);
                cardmanager.updateWinning(currentPlayer);
                currentPlayer=(currentPlayer+1)%4;
                if(currentPlayer==0) currentPlayer=4;
                currentOrder++;
                handler.postDelayed(() ->startRound(), 500);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
    private void playCardWithAnimation2(String card, int position,int Player) {
        View cardView;
        int[] startLocation = new int[2];
        int[] endLocation = new int[2];

        switch (Player){
            case 2:
                cardView = recyclerViewPlayer2.getLayoutManager().findViewByPosition(position);
                cardView.getLocationOnScreen(startLocation);
                card2.getLocationOnScreen(endLocation);
                break;
            case 3:
                cardView = recyclerViewPlayer3.getLayoutManager().findViewByPosition(position);
                cardView.getLocationOnScreen(startLocation);
                card3.getLocationOnScreen(endLocation);
                break;
            case 4:
                cardView = recyclerViewPlayer4.getLayoutManager().findViewByPosition(position);
                cardView.getLocationOnScreen(startLocation);
                card4.getLocationOnScreen(endLocation);
                break;
            default:
                cardView=null;
        }
        if (cardView == null) return;

        // Calculate the start and end coordinates for the animation


        float startX = startLocation[0];
        float startY = startLocation[1];
        float endX = endLocation[0];
        float endY = endLocation[1];

        // Create the animation
        TranslateAnimation animation = new TranslateAnimation(0, endX - startX, 0, endY - startY);
        animation.setDuration(2000);
        animation.setFillAfter(true);



        // Update the card1 ImageView after the animation
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                removeCardFromPlayer(currentPlayer,card);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                // Update the card1 ImageView with the played card
                int cardResId = getResources().getIdentifier(card, "drawable", getPackageName());
                switch (Player){
                    case 2:
                        card2.setImageResource(cardResId);
                        break;
                    case 3:
                        card3.setImageResource(cardResId);
                        break;
                    case 4:
                        card4.setImageResource(cardResId);
                        break;
                }
                cardmanager.updateWinning(currentPlayer);
                currentPlayer=(currentPlayer+1)%4;
                if(currentPlayer==0) currentPlayer=4;
                currentOrder++;
                handler.postDelayed(() ->startRound(), 500);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        cardView.startAnimation(animation);
    }
    private void cleanCardWithAnimation(ImageView[] cards, int winner) {
        View cardView ;
        int[] startLocation = new int[2];
        int[] endLocation = new int[2];
        if(winner==1||winner==3){
            cardView= recyclerViewPlayer1;

        }
        else {
            cardView= recyclerViewPlayer4;
        }
        if (cardView == null) return;

        // Calculate the start and end coordinates for the animation
        cardView.getLocationOnScreen(endLocation);
        float endX = endLocation[0];
        float endY = endLocation[1];

        card1.getLocationOnScreen(startLocation);
        float startX = startLocation[0];
        float startY = startLocation[1];
        TranslateAnimation animation1 = new TranslateAnimation(0, endX - startX, 0, endY - startY);
        card2.getLocationOnScreen(startLocation);
        startX = startLocation[0];
        startY = startLocation[1];
        TranslateAnimation animation2 = new TranslateAnimation(0, endX - startX, 0, endY - startY);
        card3.getLocationOnScreen(startLocation);
        startX = startLocation[0];
        startY = startLocation[1];
        TranslateAnimation animation3 = new TranslateAnimation(0, endX - startX, 0, endY - startY);
        card4.getLocationOnScreen(startLocation);
        startX = startLocation[0];
        startY = startLocation[1];
        TranslateAnimation animation4 = new TranslateAnimation(0, endX - startX, 0, endY - startY);

        animation1.setDuration(300);
        animation2.setDuration(300);
        animation3.setDuration(300);
        animation4.setDuration(300);

        // Start the animation
        card1.startAnimation(animation1);
        card2.startAnimation(animation2);
        card3.startAnimation(animation3);
        card4.startAnimation(animation4);

        // Update the card1 ImageView after the animation
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                for (ImageView player : cards) {
                    player.setImageDrawable(null);
                }
                cardmanager.setSuit(null);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}



