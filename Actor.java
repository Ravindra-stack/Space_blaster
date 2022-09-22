/*

@Author-- Dr. Tiny
version-- 1.0

This class will contain the x and y coordinate of any visible animation on canvas
this class also contains the image list of an object
this contains a method autoact which will be used to perform some thing 
on an object which is not being control by keyboard
this simple class is more powerful than you think
this class also contains the method for collision testing
which is quite efficient and easy


*/



import javafx.scene.image.*;
import javafx.scene.canvas.*;
class Actor
{
	int x,y,fps,speed=2;
	GraphicsContext gc;
	int ySpeed=1;
	int life=1;
	int frame=0;
	int rate;
	int hight,width;
	int health=400;
	Image[] explosionImage=new Image[9];
	int explosionImageCount=9;
	Image[] image;
	Image[] temp;
	public Actor(int x,int y,int width,int hight,int fps,int rate,Image[] image)
	{
		this.x=x;                   
		this.y=y;
		this.fps=fps;
		this.width=width;
		this.hight=hight;
		this.rate=rate;
		this.image=image;  //actualimage
		this.temp=image;
		for(int i=0;i<9;i++)
			explosionImage[i]=new Image("anim/Explosion2/Default/"+i+".png");
	}
	public int get()
	{
		return x;
	}
	public void move()
	{
		if(life==1)
		{
			x+=speed;
			y+=ySpeed;
			frameChange();
			
			if(x<=0)
			{
				x=1;
				speed*=-1;
			}
			else if(x>=800)
			{
				x=799;
				speed*=-1;
			}
			if(y>=800)
			{
				y=-40;
			}
		}
		else if(life==500)
		{
			frame++;
			x+=speed;
			y+=ySpeed;
			if(frame>=fps*rate)
			{
				frame=0;
				life=1;
				fps=16;
				rate=1;
				x = (int)Math.floor(Math.random()*(800));
				y = (int)Math.floor(Math.random()*(0-(-300)+1)+(-300));
				image=temp;
				
			}
		}




		if(health<=0)
		{
			System.out.println("Explodead");
			image=explosionImage;//Chaging Image when  health is zero
			fps=explosionImageCount;
			rate=8;
			life=500; //explosion State
			health=400;
			frame=0;
		}
	}
	void frameChange()// this will change the image over time to form a illusion of animation
	{

		frame++;
		if(frame>=fps*rate)
		{
			frame=0;
		}
	}
	void Autoact(GraphicsContext gc)// this is used for acting of enemy
	{
	    
		//gc=mycanvas.getGraphicsContext2D();
		move(); // this method should be defined in this class 
		gc.drawImage(image[frame/rate],x,y);
	}

//does not to return any thing but for debugging purpose I return a boolean
	boolean isTouch(Actor actor)
	{
		if(life==1 && actor.life==1)
			if(((x+width>actor.x) && (actor.x+actor.width>x)) && ((y+hight>actor.y) && (actor.y+actor.hight>y)))
			{
				System.out.println("Collision: Now health is -->"+actor.health);
				health-=10;
				actor.health-=5;
				return true;
			}
		return false;

	}
}