class Tes
{
	public static void main(String[] args) {
		int array[] = {1,2,3,4};
		System.out.println(collectData(array));
	}
	static int collectData(int[] array)
	{
		int size = array.length;
		int sum = 0;

		for(int i = 0;i<size;i++)
		{
			int flag = 0;
			for(int j = i+1;j<size;j++)
			{
				if(array[j]>array[i])
				{
					flag = 1;
					sum+=array[j];
					break;
				}
			}
			if(flag==0)
				sum+=-1;
		}
		return sum;
	}
}