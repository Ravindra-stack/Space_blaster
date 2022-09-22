// This is only the assistance class to find the location of any.. 
// object on the screen and also to create them
//it also includes a strong Collision checking Method
class Rect
{
    private double x,y,width,hight;
    
    Rect(double x, double y, double width , double hight)
    {
        this.x=x;
        this.y=y;
        this.width=width;
        this.hight=hight;
    }
    public double getX()
    {
        return this.x;
        
    }
    public double getY()
    {
        return this.y;
    }
    public double getWidth()
    {
        return this.width;
    }
    public double getHight()
    {
        return this.hight;
    }
    public void setX(double x)
    {
        this.x=x;
    }
    public void setY(double y)
    {
        this.y=y;
    }
    public void setWidth(double widht)
    {
        this.width=width;
    }
 
    
    public void setHight(double hight)
    {
        this.hight=hight;
    }
    public void autoAct()
    {
        this.y-=10;
    }
    //collision checking Method , you don't need to understand it use it like abstract
    //this is based on geometry
    void checkTouch(Actor actor)
    {
        if(actor.life==1)
            if(((x+width>actor.x) && (actor.x+actor.width>x)) && ((y+hight>actor.y) && (actor.y+actor.hight>y)))
            {
                System.out.println("Goli lag gaya|||-->"+actor.health);//For debugging
                actor.health-=40;
                y=0;
            }
    }
}
