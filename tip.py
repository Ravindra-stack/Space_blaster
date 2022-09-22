array = []
        max_len = 0;
        for i in range(0,len(s)):
            for j in range(i,len(s)):
                array.append(s[j])
                if(len(array)==len(set(array))):
                    if(len(array)>=max_len):
                        #print(array," is now the max_len")
                        max_len = len(array)
                else:
                   # print(array," got rejected")
                    break
            array = []
        return max_len