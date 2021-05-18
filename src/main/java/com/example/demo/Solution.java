package com.example.demo;


import java.io.*;
        import java.math.*;
        import java.security.*;
        import java.text.*;
        import java.util.*;
        import java.util.concurrent.*;
        import java.util.function.*;
        import java.util.regex.*;
        import java.util.stream.*;
        import static java.util.stream.Collectors.joining;
        import static java.util.stream.Collectors.toList;



class Result {

    /*
     * Complete the 'smallestNegativeBalance' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts 2D_STRING_ARRAY debts as parameter.
     */

    public static List<String> smallestNegativeBalance(List<List<String>> debts) {
        Map<String,Integer> debtMap = new HashMap<>();
        for (List<String> list: debts) {
            String borrower =list.get(0);
            String lender = list.get(1);
            Integer amount = Integer.parseInt(list.get(2));
            if(debtMap.get(borrower)==null){
                debtMap.put(borrower,-amount);
            }else{
                Integer tempAmount = debtMap.get(borrower);
                tempAmount = tempAmount - amount;
                debtMap.put(borrower,tempAmount);
            }
            if(debtMap.get(lender)==null){
                debtMap.put(lender,amount);
            }else{
                Integer tempAmount = debtMap.get(lender);
                tempAmount = tempAmount + amount;
                debtMap.put(lender,tempAmount);
            }

        }
        List<String> deptList = new ArrayList<>();

        int lowest = 0;
        Integer deptValue;
        for (String key : debtMap.keySet()){
            deptValue =debtMap.get(key) ;
            if(deptValue<0){
                if(deptValue<lowest){
                    lowest=deptValue;
                    deptList = new ArrayList<>();
                    deptList.add(key);
                }else if(deptValue==lowest){
                    deptList.add(key);
                }
            }
        }
        if(deptList.size()==0){
            deptList.add("Nobody has a negative balance");
        }else {
            Collections.sort(deptList);
        }
        return deptList;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
      //  BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int debtsRows = Integer.parseInt(bufferedReader.readLine().trim());
        int debtsColumns = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<String>> debts = new ArrayList<>();

        IntStream.range(0, debtsRows).forEach(i -> {
            try {
                debts.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<String> result = Result.smallestNegativeBalance(debts);
        System.out.println(result.get(0));
        bufferedReader.close();
       /* bufferedWriter.write(
                result.stream()
                        .collect(joining("\n"))
                        + "\n"
        );


        bufferedWriter.close();*/
    }
}
