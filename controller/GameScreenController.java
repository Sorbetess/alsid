package alsid.controller;

import alsid.model.asset.*;
import alsid.model.chance.GetOutOfJailChance;
import alsid.model.game.*;
import alsid.model.space.*;

import java.io.IOException;
import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class GameScreenController
{
    private Game game;
    private boolean endGame = false;
    private int diceRoll;
    private int[][] index;

    //ArrayLists to group the elements in the GUI
    private ArrayList<Label>        spaceLabels;
    private ArrayList<Button>       spaceButtons;
    private ArrayList<ImageView>    sprites;
    private ArrayList<Label>        playerDisplays;
    private MenuBar[][]             menus;
    private ArrayList<ImageView>    boxes;

    private int     swapposition1   = -1;
    private int     swapposition2   = -1;
    private int     offer           = 0;
    private boolean doneSwapping    = false;
    private boolean isTrading       = false;
    private boolean isChoosingProp  = false;

    @FXML
    GridPane board;

    @FXML
    HBox mainHBox;

    //Labels used to display the board spaces.
    @FXML
    Label   str0, str1, str2, str3, str4, str5, str6, str7,
            str8, str9, str10, str11, str12, str13, str14, str15,
            str16, str17, str18, str19, str20, str21, str22, str23,
            str24, str25, str26, str27, str28, str29, str30, str31;

    @FXML
    Button space0, space1, space2, space3, space4, space5, space6, space7,
            space8, space9, space10, space11, space12, space13, space14, space15,
            space16, space17, space18, space19, space20, space21, space22, space23,
            space24, space25, space26, space27, space28, space29, space30, space31;

    //ImageView for displaying the player sprites.
    @FXML
    ImageView sprite1, sprite2, sprite3, sprite4;

    @FXML
    Label bankMoney;

    //Label for displaying messages/narrations in-game.
    @FXML
    Label message, spaceInfoDisplay;

    @FXML
    ImageView spaceInfoBox;

    //Label for displaying info about the players (name and amount of money left).
    @FXML
    Label player1display, player2display, player3display, player4display;

    @FXML
    MenuBar menu1top, menu1bottom, menu2top, menu2bottom,
            menu3top, menu3bottom, menu4top, menu4bottom;

    @FXML
    ImageView box1, box2, box3, box4;

    //Button for rolling the dice and starting the game (after swapping is finished).
    @FXML
    Button rollDice, startGame;

    //Button for purchasing an asset, paying rent, developing a property, and ending the turn.
    @FXML
    Button purchase, payRent, trade, develop, cancelTrade, agree, pass;

    @FXML
    Button drawChance, payTax;

    //delete later
    @FXML
    TextField diceNumber;

    @FXML
    Button bankrupt;

    public GameScreenController(Game game)
    {
        this.game = game;
    }

    @FXML
    public void initialize()
    {
        spaceLabels     = new ArrayList<>();
        spaceButtons    = new ArrayList<>();
        sprites         = new ArrayList<>();
        playerDisplays  = new ArrayList<>();
        menus           = new MenuBar[4][2];
        boxes           = new ArrayList<>();

        spaceLabels.addAll(Arrays.asList(str0, str1, str2, str3, str4, str5, str6, str7,
                str8, str9, str10, str11, str12, str13, str14, str15,
                str16, str17, str18, str19, str20, str21, str22, str23,
                str24, str25, str26, str27, str28, str29, str30, str31));

        spaceButtons.addAll(Arrays.asList(space0, space1, space2, space3, space4, space5, space6, space7,
                space8, space9, space10, space11, space12, space13, space14, space15,
                space16, space17, space18, space19, space20, space21, space22, space23,
                space24, space25, space26, space27, space28, space29, space30, space31));

        sprites.addAll(Arrays.asList(sprite1, sprite2, sprite3, sprite4));

        playerDisplays.addAll(Arrays.asList(player1display, player2display, player3display, player4display));

        menus[0][0] = menu1top;
        menus[0][1] = menu1bottom;
        menus[1][0] = menu2top;
        menus[1][1] = menu2bottom;
        menus[2][0] = menu3top;
        menus[2][1] = menu3bottom;
        menus[3][0] = menu4top;
        menus[3][1] = menu4bottom;

        boxes.addAll(Arrays.asList(box1, box2, box3, box4));

        for(int i = 0; i <= 31; i++)
        {
            ImageView imgView = game.getBoard().getSpaces().get(i).getImage();
            imgView.setFitWidth(70.0);
            imgView.setFitHeight(70.0);
            imgView.setPickOnBounds(true);
            imgView.setPreserveRatio(true);

            spaceButtons.get(i).setPadding(Insets.EMPTY);
            spaceButtons.get(i).setGraphic(imgView);
            spaceLabels.get(i).setText(game.getBoard().getSpaces().get(i).toString());

            GridPane.setHalignment(spaceLabels.get(i), HPos.CENTER);

            game.getBoard().getSpaces().get(i).setPosition(i);

            if(i == 0 || i == 8 || i == 16 || i == 24)
            {
                spaceButtons.get(i).setDisable(true);
            }
        }

        index = new int[32][2];
        for(int j = 0; j < 8; j++)
        {
            index[j] = new int[]{0, j};
            index[j + 9] = new int[]{j + 1, 8};
            index[j + 16] = new int[]{8, 8 - j};
            index[j + 24] = new int[]{8 - j, 0};
        }
        index[8] = new int[]{0, 8};

        message.setText("Welcome to Around La Salle in 80 Days! Please set up the board. Click on two spaces to swap them.");

        updatePlayers();

        sprite1.setImage(new Image("/alsid/assets/sprite-lasalle-green.png"));
        sprite2.setImage(new Image("/alsid/assets/sprite-lasalle-purple.png"));
        sprite3.setImage(new Image("/alsid/assets/sprite-lasalle-red.png"));
        sprite4.setImage(new Image("/alsid/assets/sprite-lasalle-yellow.png"));

        sprite1.setTranslateX(10);
        sprite2.setTranslateX(40);
        sprite3.setTranslateX(10);
        sprite4.setTranslateX(40);

        sprite1.setTranslateY(-10);
        sprite2.setTranslateY(-10);
        sprite3.setTranslateY(20);
        sprite4.setTranslateY(20);


        bankMoney.setText("The Bank currently has $" + game.getBank().getMoney() + ".");
    }

    @FXML
    protected void handleRollDiceButtonAction(ActionEvent event)
    {
        int oldPosition, newPosition;
        Space landedSpace;

        rollDice.setVisible(false);
        pass.setVisible(false);

        Player currentPlayer = game.getPlayers().get(game.getCurrentPlayer());
        /**int number = new Random().nextInt(6) + 1;

        diceRoll = number;*/

        int number = Integer.parseInt(diceNumber.getText());
        diceRoll = number;

        oldPosition = currentPlayer.getPosition();

        if(currentPlayer.getPosition() + number > 31)
            newPosition = currentPlayer.getPosition() + number - 32;
        else newPosition = currentPlayer.getPosition() + number;

        landedSpace = game.getBoard().getSpaces().get(newPosition);

        Timeline move = new Timeline(new KeyFrame(Duration.millis(350), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event)
            {
                board.getChildren().remove(sprites.get(game.getCurrentPlayer()));
                if (currentPlayer.getPosition() == 31)
                {
                    board.add(sprites.get(game.getCurrentPlayer()), index[0][1], index[0][0]);
                    currentPlayer.setPosition(0);
                }
                else
                {
                    board.add(sprites.get(game.getCurrentPlayer()), index[currentPlayer.getPosition() + 1][1], index[currentPlayer.getPosition() + 1][0]);
                    currentPlayer.setPosition(currentPlayer.getPosition() + 1);
                }
            }
        }));

        move.setCycleCount(number);
        move.play();

        if(landedSpace instanceof Property)
        {
            message.setText(currentPlayer.getName() + " rolled a " +
                    number + "! Landed on " + ((Property) landedSpace).getName() +
                    " (" + ((Property) landedSpace).getHouseCount() + "\uD83C\uDFE0). ");
        }
        else message.setText(currentPlayer.getName() + " rolled a " + number + "! Landed on " + landedSpace.toString() + ". ");

        move.setOnFinished(e ->
        {
            try
            {
                onLand(oldPosition, newPosition, currentPlayer, landedSpace);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    protected void onLand(int oldPosition, int newPosition, Player currentPlayer, Space landedSpace) throws IOException {
        if (oldPosition == 24)
        {
            boolean hasGOJF = false;

            for(int i = 0; i < currentPlayer.getChanceCards().size(); i++)
            {
                if (currentPlayer.getChanceCards().get(i) instanceof GetOutOfJailChance)
                {
                    hasGOJF = true;
                }
            }

            if(hasGOJF == false)
            {
                currentPlayer.add(-50);
                message.setText(message.getText() + "Paid $50 to get out of jail. ");
            }
            else message.setText(message.getText() + "Used Get Out Of Jail Card. ");

            updatePlayers();
        }

        //landed on START space
        if (newPosition == 0)
        {
            game.getBank().payTo(currentPlayer, 200);
            bankMoney.setText("The Bank currently has $" + game.getBank().getMoney() + ".");
            message.setText(message.getText() + "Collected $200. ");

            nextTurn(true);
        }

        //landed on Free Parking space
        else if (newPosition == 8)
        {
            message.setText(message.getText() + "For now, you may rest. ");

            nextTurn(true);
        }

        //landed on Community Service (CHURCH) space
        else if (newPosition == 16)
        {
            currentPlayer.add(-50);
            message.setText(message.getText() + "Donated $50 to the bank. ");

            nextTurn(true);
        }

        //landed on Jail (SDFO) space
        else if (newPosition == 24)
        {
            message.setText(message.getText() + "Pay a fine of $50 later. ");
            nextTurn(true);
        }



        //landed on a Property
        else if (landedSpace instanceof Property)
        {
            ((Property) landedSpace).incFootCount();

            //landed on own Property
            if (((Property) landedSpace).getOwner() == currentPlayer)
            {
                if (((Property) landedSpace).canDevelop())
                {
                    develop.setVisible(true);
                }
                else pass.setVisible(true);
            }

            //landed on unowned Property
            else if (!((Property) landedSpace).isOwned())
            {
                if(((Property) landedSpace).getPrice() <= currentPlayer.getMoney())
                    purchase.setVisible(true);

                pass.setVisible(true);
            }

            //landed on someone else's Property
            else if(((Property) landedSpace).getOwner() != currentPlayer)
            {
                Player other = ((Property) landedSpace).getOwner();

                if (currentPlayer.getProperties().size() > 0)
                    trade.setVisible(true);

                payRent.setVisible(true);
            }

            spaceInfoDisplay.setText(((Asset) landedSpace).getInfo());

            spaceInfoBox.setVisible(true);
            spaceInfoDisplay.setVisible(true);
        }

        //landed on a Railroad
        else if (landedSpace instanceof Railroad)
        {
            spaceInfoDisplay.setText(((Asset) landedSpace).getInfo());

            if (((Railroad) landedSpace).getOwner() == currentPlayer)
            {
                message.setText(message.getText() + "(own railroad)");
                pass.setVisible(true);
            }

            //landed on unowned Railroad
            else if (!((Railroad) landedSpace).isOwned())
            {
                if(((Railroad) landedSpace).getPrice() <= currentPlayer.getMoney())
                    purchase.setVisible(true);

                pass.setVisible(true);
            }

            //landed on someone else's Railroad
            else if(((Railroad) landedSpace).getOwner() != currentPlayer)
            {
                Player other = ((Railroad) landedSpace).getOwner();

                payRent.setVisible(true);
            }

            spaceInfoBox.setVisible(true);
            spaceInfoDisplay.setVisible(true);
        }

        //landed on a Utility
        else if (landedSpace instanceof Utility)
        {
            spaceInfoDisplay.setText(((Asset) landedSpace).getInfo());

            if (((Utility) landedSpace).getOwner() == currentPlayer)
            {
                pass.setVisible(true);
            }

            //landed on unowned Railroad
            else if (!((Utility) landedSpace).isOwned())
            {
                if(((Utility) landedSpace).getPrice() <= currentPlayer.getMoney())
                    purchase.setVisible(true);

                pass.setVisible(true);
            }

            //landed on someone else's Railroad
            else if(((Utility) landedSpace).getOwner() != currentPlayer)
            {
                Player other = ((Utility) landedSpace).getOwner();
                message.setText(message.getText() + "(" + other.getName() + "'s utility)");

                payRent.setVisible(true);
            }

            spaceInfoBox.setVisible(true);
            spaceInfoDisplay.setVisible(true);
        }

        //landed on the Income Tax Space
        else if (landedSpace instanceof IncomeTaxSpace)
        {
            payTax.setVisible(true);
        }

        //landed on the Luxury Tax Space
        else if (landedSpace instanceof LuxuryTaxSpace)
        {
            payTax.setVisible(true);
        }

        //landed on the Chance Space
        else if (landedSpace instanceof ChanceSpace)
        {
            drawChance.setVisible(true);
        }

        if (newPosition != 0 && newPosition < oldPosition)
        {
            game.getBank().payTo(currentPlayer, 200);
            message.setText(message.getText() + " Collected $200. ");
            bankMoney.setText("The Bank currently has $" + game.getBank().getMoney() + ".");

            updatePlayers();
        }
    }



    @FXML
    protected void handleStartGameButtonAction(ActionEvent event)
    {
        rollDice.setVisible(true);
        pass.setVisible(false);
        startGame.setVisible(false);

        spaceButtons.get(0).setDisable(false);
        spaceButtons.get(8).setDisable(false);
        spaceButtons.get(16).setDisable(false);
        spaceButtons.get(24).setDisable(false);

        for(int i = 0; i < game.getPlayers().size(); i++)
            sprites.get(i).setVisible(true);

        doneSwapping = true;

        message.setText("Alright! Start rolling the dice, " + game.getPlayers().get(game.getCurrentPlayer()).getName() + "!");
    }

    @FXML
    protected void handleClickSpaceButtonAction (ActionEvent event) throws IOException {

        //if player is still currently swapping spaces

        if(!doneSwapping)
        {
            if(swapposition1 == -1)
            {
                for (int i = 0; i <= 31; i++)
                {
                    if (spaceButtons.get(i) == event.getSource())
                        swapposition1 = i;
                }

                spaceButtons.get(swapposition1).setDisable(true);
            }
            else if(swapposition2 == -1)
            {
                for (int i = 0; i <= 31; i++)
                {
                    if (spaceButtons.get(i) == event.getSource())
                        swapposition2 = i;
                }

                ImageView imgTemp1 = game.getBoard().getSpaces().get(swapposition1).getImage();
                ImageView imgTemp2 = game.getBoard().getSpaces().get(swapposition2).getImage();
                String strTemp1 = game.getBoard().getSpaces().get(swapposition1).toString();
                String strTemp2 = game.getBoard().getSpaces().get(swapposition2).toString();

                game.getBoard().getSpaces().get(swapposition2).setPosition(swapposition1);
                game.getBoard().getSpaces().get(swapposition1).setPosition(swapposition2);
                spaceButtons.get(swapposition1).setGraphic(imgTemp2);
                spaceButtons.get(swapposition2).setGraphic(imgTemp1);
                spaceLabels.get(swapposition1).setText(strTemp2);
                spaceLabels.get(swapposition2).setText(strTemp1);

                spaceButtons.get(swapposition1).setDisable(false);

                Collections.swap(game.getBoard().getSpaces(), swapposition1, swapposition2);

                swapposition1 = -1;
                swapposition2 = -1;
            }
        }

        //if player is choosing which property to trade

        else if (isTrading)
        {
            int i;

            for (i = 0; i <= 31; i++)
            {
                if (spaceButtons.get(i) == event.getSource())
                    this.offer = i;
            }

            Player currentPlayer = game.getPlayers().get(game.getCurrentPlayer());
            Space currentSpace = game.getBoard().getSpaces().get(currentPlayer.getPosition());
            Space offeredSpace = game.getBoard().getSpaces().get(this.offer);

            message.setText(((Property)currentSpace).getOwner().getName() + ", " + currentPlayer.getName() +
                    " offered " + ((Property) offeredSpace).getName() + ". Please click agree or cancel.");

            spaceButtons.get(this.offer).setDisable(true);
            agree.setVisible(true);
        }

        //if player is choosing which property to use chance card on

        else if (isChoosingProp)
        {
            isChoosingProp = false;
            nextTurn(true);
        }
    }

    @FXML
    protected void handlePassButtonAction(ActionEvent event) throws IOException
    {
        nextTurn(false);
    }

    @FXML
    protected void handlePurchaseButtonAction (ActionEvent e) throws IOException {
        Player currentPlayer = game.getPlayers().get(game.getCurrentPlayer());
        Space currentSpace = game.getBoard().getSpaces().get(currentPlayer.getPosition());

        if(currentSpace instanceof Asset)
        {
            currentPlayer.purchase((Asset) currentSpace);
            message.setText(currentPlayer.getName() + " has purchased " + ((Asset) currentSpace).getName() + ". ");
        }

        nextTurn(true);
    }

    @FXML
    protected void handlePayRentButtonAction (ActionEvent e) throws IOException {
        Player currentPlayer = game.getPlayers().get(game.getCurrentPlayer());
        Space currentSpace = game.getBoard().getSpaces().get(currentPlayer.getPosition());

        if(currentSpace instanceof Property)
        {
            currentPlayer.payRent((Property) currentSpace);
            message.setText(currentPlayer.getName() + " paid rent to " + ((Property) currentSpace).getOwner().getName() + ". ");
        }
        else if(currentSpace instanceof Railroad)
        {
            currentPlayer.payRent((Railroad) currentSpace);
            message.setText(currentPlayer.getName() + " paid rent to " + ((Railroad) currentSpace).getOwner().getName() + ". ");
        }
        else if(currentSpace instanceof Utility)
        {
            currentPlayer.payRent(((Utility) currentSpace), diceRoll);
            message.setText(currentPlayer.getName() + " paid rent to " + ((Utility) currentSpace).getOwner().getName() + ". ");
        }

        nextTurn(true);
    }

    @FXML
    protected void handlePayTaxButtonAction (ActionEvent e) throws IOException {
        Player currentPlayer = game.getPlayers().get(game.getCurrentPlayer());
        Space currentSpace = game.getBoard().getSpaces().get(currentPlayer.getPosition());

        if(currentSpace instanceof IncomeTaxSpace)
        {
            currentSpace.onLand(currentPlayer);
            message.setText(currentPlayer.getName() + " paid income tax to the bank. ");
        }
        else if(currentSpace instanceof LuxuryTaxSpace)
        {
            currentSpace.onLand(currentPlayer);
            message.setText(currentPlayer.getName() + " paid luxury tax to the bank. ");
        }

        //TODO find way to get how much tax was deducted, then increment bank cash

        nextTurn(true);
    }

    @FXML
    protected void handleDevelopButtonAction (ActionEvent e) throws IOException {
        Player currentPlayer = game.getPlayers().get(game.getCurrentPlayer());
        Space currentSpace = game.getBoard().getSpaces().get(currentPlayer.getPosition());

        currentPlayer.develop((Property) currentSpace);

        spaceLabels.get(currentPlayer.getPosition()).setText(currentSpace.toString());

        nextTurn(true);
    }

    @FXML
    protected void handleTradeButtonAction (ActionEvent e)
    {
        Player currentPlayer = game.getPlayers().get(game.getCurrentPlayer());

        payRent.setVisible(false);
        cancelTrade.setVisible(true);
        isTrading = true;

        for(int i = 0; i <= 31; i++)
        {
            if(game.getBoard().getSpaces().get(i) instanceof Property)
            {
                if (((Property) game.getBoard().getSpaces().get(i)).getOwner() != currentPlayer)
                    spaceButtons.get(i).setDisable(true);
            }
            else spaceButtons.get(i).setDisable(true);
        }

        message.setText(currentPlayer.getName() + ", please choose which property to offer, or click cancel to stop trading.");
    }

    @FXML
    protected void handleCancelTradeButtonAction (ActionEvent e)
    {
        trade.setVisible(true);
        payRent.setVisible(true);

        cancelTrade.setVisible(false);
        agree.setVisible(false);

        for(int i = 0; i <= 31; i++)
        {
            if (spaceButtons.get(i).isDisabled()) {
                spaceButtons.get(i).setDisable(false);
            }
        }

        message.setText("Cancelled trading.");

        isTrading = false;
    }

    @FXML
    protected void handleAgreeButtonAction (ActionEvent e) throws IOException
    {
        Player currentPlayer = game.getPlayers().get(game.getCurrentPlayer());
        Space currentSpace = game.getBoard().getSpaces().get(currentPlayer.getPosition());
        Space offeredSpace = game.getBoard().getSpaces().get(this.offer);

        currentPlayer.trade(((Asset)currentSpace).getOwner(), (Asset) currentSpace, (Asset) offeredSpace);

        isTrading = false;

        message.setText("Traded " + ((Asset) game.getBoard().getSpaces().get(this.offer)).getName() +
                " for " + ((Asset) currentSpace).getName() +
                " with " + ((Asset)currentSpace).getOwner().getName() + ". ");

        for(int j = 0; j <= 31; j++)
        {
            if (spaceButtons.get(j).isDisabled()) {
                spaceButtons.get(j).setDisable(false);
            }
        }

        updatePlayers();

        nextTurn(true);
    }

    @FXML
    protected void handleDrawChanceButtonAction (ActionEvent e) throws IOException {
        Player currentPlayer = game.getPlayers().get(game.getCurrentPlayer());
        Space currentSpace = game.getBoard().getSpaces().get(currentPlayer.getPosition());


        //TODO
        //currentSpace.onLand(currentPlayer);

        nextTurn(false);
    }

    /**
     * This method updates the GUI.
     */
    private void updatePlayers()
    {
        int i, j;

        //Update player info for each player

        for(i = 0; i < game.getPlayers().size(); i++)
        {
            //Display player's info (name and amount of money)

            boxes.get(i).setVisible(true);
            menus[i][0].setVisible(true);
            menus[i][1].setVisible(true);

            menus[i][0].getMenus().get(0).getItems().clear();
            menus[i][0].getMenus().get(1).getItems().clear();
            menus[i][1].getMenus().get(0).getItems().clear();
            menus[i][1].getMenus().get(1).getItems().clear();

            playerDisplays.get(i).setText(game.getPlayers().get(i).getName() +
                    "\n$" + game.getPlayers().get(i).getMoney());
            playerDisplays.get(i).setVisible(true);

            //Update player's assets

            if(game.getPlayers().get(i).getProperties().size() == 0)
                menus[i][0].getMenus().get(0).getItems().add(new MenuItem("No Properties"));
            else
            {
                for (j = 0; j < game.getPlayers().get(i).getProperties().size(); j++)
                {
                    MenuItem text = new MenuItem(game.getPlayers().get(i).getProperties().get(j).getName());
                    text.setStyle("-fx-text-fill: " + game.getPlayers().get(i).getProperties().get(j).getHexColor());
                    menus[i][0].getMenus().get(0).getItems().add(text);
                }
            }

            if(game.getPlayers().get(i).getUtilities().size() == 0)
                menus[i][0].getMenus().get(1).getItems().add(new MenuItem("No Utilities"));
            else
            {
                for (j = 0; j < game.getPlayers().get(i).getUtilities().size(); j++)
                {
                    MenuItem text = new MenuItem(game.getPlayers().get(i).getUtilities().get(j).getName());
                    menus[i][0].getMenus().get(1).getItems().add(text);
                }
            }

            if(game.getPlayers().get(i).getRailroads().size() == 0)
                menus[i][1].getMenus().get(0).getItems().add(new MenuItem("No Railroads"));
            else
            {
                for (j = 0; j < game.getPlayers().get(i).getRailroads().size(); j++)
                {
                    MenuItem text = new MenuItem(game.getPlayers().get(i).getRailroads().get(j).getName());
                    menus[i][1].getMenus().get(0).getItems().add(text);
                }
            }

            if(game.getPlayers().get(i).getChanceCards().size() == 0)
                menus[i][1].getMenus().get(1).getItems().add(new MenuItem("No Chance Cards"));
            else
            {
                for (j = 0; j < game.getPlayers().get(i).getChanceCards().size(); j++)
                {
                    MenuItem text;
                    if(game.getPlayers().get(i).getChanceCards().get(j) instanceof GetOutOfJailChance)
                        text = new MenuItem("Get Out Of Jail");

                    menus[i][1].getMenus().get(1).getItems().add(new MenuItem(game.getPlayers().get(i).getRailroads().get(j).getName()));
                }
            }
        }
    }

    private void nextTurn(boolean actionPerformed) throws IOException {
        updatePlayers();

        if (game.checkGameEnd() != 0)
        {
            //message.setText("Game has ended!");

            GameEndController endController = new GameEndController(game);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/alsid/view/GameEnd.fxml"));
            loader.setController(endController);
            GridPane newPane = loader.load();
            mainHBox.getChildren().setAll(newPane);
        }
        else
        {
            game.nextTurn();
            if(actionPerformed)
                message.setText(message.getText() +  "It's now " + game.getPlayers().get(game.getCurrentPlayer()).getName() +
                        "'s turn. Roll the dice!");
            else message.setText("It's now " + game.getPlayers().get(game.getCurrentPlayer()).getName() +
                    "'s turn. Roll the dice!");

            purchase.setVisible(false);
            payRent.setVisible(false);
            trade.setVisible(false);
            agree.setVisible(false);
            cancelTrade.setVisible(false);
            develop.setVisible(false);
            pass.setVisible(false);
            drawChance.setVisible(false);
            payTax.setVisible(false);

            spaceInfoDisplay.setVisible(false);
            spaceInfoBox.setVisible(false);

            rollDice.setVisible(true);
        }
    }

    @FXML
    protected void handleBankruptButtonAction() throws IOException
    {
        game.getPlayers().get(0).add(-5000);

        nextTurn(true);
    }
}