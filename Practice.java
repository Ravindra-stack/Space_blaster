import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.shape.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.animation.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.util.Duration;
import java.util.HashSet;
import java.util.*;
import javafx.scene.image.*;
//______________________________________________________________________________________________________
 class Practice extends Application
{
    GraphicsContext gc;
    int x=350,y=350;
    int bulletCount=0;
    int fireControl=0;   //it will use to slow down the rate of fire of bullet
    int speed=2,end=0,start=0,s=0;
    int shipSpeed=3;  //spped of the space-ship
    Scene myscene;
    int pauseCount;
    int pause=1;
    static HashSet<String> currentlyActiveKeys;
    ArrayList<Actor> enemies = new ArrayList<Actor>();
    public static void main(String[] args)
    {
        launch(args);
        
    }
    public void start(Stage mystage)
    {
    //_______________________________________________________________________________________________
        //Creating Image type objects to store our animations
        Image bg=new Image("bg.png");
        Image[] shipLeft=new Image[15];
        Image[] shipRight=new Image[15];
        Image[] shipDefault=new Image[15];
        Image[] enemy_1Image=new Image[16];
        Image[] enemy_2Image=new Image[16];
        Image[] enemy_3Image=new Image[16];
        Image[] enemy_4Image=new Image[16];
        Image[] flare=new Image[8]; //special effect when firing the bullet
        //font is for the texts which will appear on screen
        Font font = Font.font("Algerian", FontWeight.BOLD, 36);
        Font font2 = Font.font("AmericanTypewriter cs", FontWeight.BOLD, 36);
        //Loading resources
        for(int i=0;i<8;i++)
            flare[i]=new Image("anim/GunFlare/Default/"+i+".png");
        for(int i=0;i<15;i++)
        {
            shipLeft[i]=new Image("anim/Player/TiltLeft/"+i+".png");
            shipRight[i]=new Image("anim/Player/TiltRight/"+i+".png");
            shipDefault[i]=new Image("anim/Player/Default/"+i+".png");
        }
        //all resources are located in anim folder
        for(int i=0;i<16;i++)
        {
            enemy_1Image[i]=new Image("anim/Enemy/Default/"+i+".png");
            enemy_2Image[i]=new Image("anim/BugEnemy/Default/"+i+".png");
            enemy_3Image[i]=new Image("anim/SpikeyEnemy/Default/"+i+".png");
            enemy_4Image[i]=new Image("anim/FighterEnemy/Default/"+i+".png");
        }
        // creating objects class Actor
        //All viewable thing on the screen are of type Actor
        Actor sh=new Actor(600,400,72,102,15,4,shipDefault);
        Actor enemy_1=new Actor(0,50,70,70,16,1,enemy_1Image);
        Actor enemy_2=new Actor(-50,0,60,78,16,1,enemy_2Image);
        Actor enemy_3=new Actor(100,-200,64,62,16,1,enemy_3Image);
        Actor enemy_4= new Actor(0,700,100,95,16,1,enemy_4Image);
        Actor gunFlare=new Actor(0,0,50,50,8,8,flare);
        //Adding enimies which will help us letter for collision testing
        enemies.add(enemy_1);
        enemies.add(enemy_2);
        enemies.add(enemy_3);
        enemies.add(enemy_4);
        //_____________________________________________________________________________________________
        mystage.setTitle("Drawing"); //You can write anything you wish
        Rect head=new Rect(0,0,10,10);
        Rect target = new Rect(100,100,40,40);
        Rect[] bullet=new Rect[300];
        Image bulletImage=new Image("anim/PlayerLaser/Default/000.png"); //It is the Image of bullet
        GridPane rootnode = new GridPane();
        rootnode.setAlignment(Pos.CENTER);
        myscene = new Scene(rootnode,800,800);
        mystage.setScene(myscene);
        Canvas mycanvas=new Canvas(800,800);
        gc=mycanvas.getGraphicsContext2D();
        gc.setFont(font);
        Button button=new Button("Right");
        Button left=new Button("Left");
        double wid=0.375;
        double wid_2=0.15;
        Button up=new Button("Up");
        Button down=new Button("down");
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount( Timeline.INDEFINITE );
        prepareActionHandlers();
        final long timeStart = System.currentTimeMillis();   
        //main Game Loop      
        KeyFrame kf = new KeyFrame(
            Duration.seconds(0.017),                // 60 FPS
            new EventHandler<ActionEvent>()
            {
                public void handle(ActionEvent ae)
                {
                    gc.setFill(Color.YELLOW);
                    gc.fillText("GAME PAUSED [P]:",400,400);  //when game is Paused this will be shown on screen
                    pauseCount++;
                    if(currentlyActiveKeys.contains("P") && (pauseCount>10))
                    {
                        System.out.println("p pressed"); //for debugging
                        pause*=-1;
                        pauseCount=0;
                    }
                    if(pause>0)
                     {
                        gc.drawImage( bg,0,0); // background Image
                        gc.setStroke(Color.BLUE);
                        gc.setFill(Color.BLUE);
                        gc.fillText("SCORE:",22,100); //Score title , you can add your own definition of adding scores
                        gc.strokeRect(20,60,154,17);
                        gc.setFill(Color.RED);
                        gc.fillRect(22,62,wid*sh.health,13);
                        if(sh.health<=0)
                            sh.health=400;
                       // gc.fillRect(head.getX(),head.getY(),head.getWidth(),head.getHight());
                        //Queue Data sturcture for elelmination of the bullet;
                        for(int i=start;i<end;i++)
                        {
                            gc.drawImage(bulletImage,bullet[i].getX(),bullet[i].getY());
                            //gc.fillRect(bullet[i].getX(),bullet[i].getY(),bullet[i].getWidth(),bullet[i].getHight());                   
                            bullet[i].autoAct();
                            for(Actor enemy:enemies)
                                bullet[i].checkTouch(enemy);
                            if(bullet[i].getY()<=100)
                            {
                                start++; // Start variable locate the Uses To know know the first Id of bullet
                            }  // Since I used Queue data Strucure to control movement of bullets.(Array Implementation)  
                        }
                        if(end==start)
                            end=0;start=0;
                        gc.fillRect(target.getX(),target.getY(),target.getWidth(),target.getHight());
                        target.setX(target.getX()+speed);
                        if(target.getX()<=100 || target.getX()>=700)
                        {
                            target.setX(target.getX()-(speed));
                            speed*=-1;
                        }
                       if (currentlyActiveKeys.contains("A"))
                        {
                            head.setX(head.getX()-10);
                            sh.x-=shipSpeed;
                            gc.drawImage( shipLeft[s/4],sh.x,sh.y); //this is the drawImage method of javaFX
                        }
                        else if (currentlyActiveKeys.contains("D"))
                        {
                            head.setX(head.getX()+10);
                            sh.x+=shipSpeed;
                            gc.drawImage(shipRight[s/4],sh.x,sh.y);
                        }
                        else
                            gc.drawImage(shipDefault[s/4],sh.x,sh.y);
                        
                        s++;
                        if(s>56)
                            s=0;
                        //Updating enimies postion per frame
                        //Note that enemy position will be Updated 60 times in one second
                        for(Actor enemy:enemies)
                        {
                            enemy.Autoact(gc);
                            
                            if(enemy.isTouch(sh))
                            {
                                
                                System.out.println("Hurray  :::"+end);
                            }
                            if(enemy.life==1) //Enemy health bar
                            {
                                gc.setFill(Color.BLUE);
                                gc.fillRect(enemy.x+5,enemy.y-10,60,5);
                                gc.setFill(Color.RED);
                                gc.fillRect(enemy.x+5,enemy.y-10,enemy.health*wid_2,5);
                            }
                        }
                        if (currentlyActiveKeys.contains("S"))
                        {
                            head.setY(head.getY()+10);
                            sh.y+=shipSpeed;
                        }
                        if (currentlyActiveKeys.contains("W"))
                        {
                             head.setY(head.getY()-10);
                             sh.y-=shipSpeed;
                        }
                        if (currentlyActiveKeys.contains("SPACE"))
                        {
                            gunFlare.frameChange();
                            gc.drawImage(flare[gunFlare.frame/gunFlare.rate],sh.x+5,sh.y-30);
                            if(fireControl>6)
                                fireControl=0;
                            if(fireControl==0)
                            {
                                //balls.add(new Rect(head.getX(),head.getY(),head.getWidth(),head.getHight()));
                                bullet[end]=new Rect(sh.x+20,sh.y+5,head.getWidth(),head.getHight());
                                // System.out.println(start+"to"+end);
                                if(end<300)
                                    end++;
                                if(end>290)
                                    end=0;start=0;
                            }
                            fireControl++;
                        }
                        else
                            fireControl=0;
                        if(currentlyActiveKeys.contains("Q"))
                            System.exit(0);
                   }
                }
            });
        rootnode.setAlignment(Pos.CENTER);
        rootnode.add(mycanvas,1,1);
        rootnode.setAlignment(Pos.CENTER);
        rootnode.setAlignment(Pos.CENTER);
        gameLoop.getKeyFrames().add( kf );
        gameLoop.play();
        mystage.show();

    }

    private void prepareActionHandlers()
    {
        // use a set so duplicates are not possible
        currentlyActiveKeys = new HashSet<String>();
        myscene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.add(event.getCode().toString());
            }
        });
        myscene.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.remove(event.getCode().toString());
            }
        });
    }

}