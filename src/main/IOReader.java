package main;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class IOReader {
    String rulesFile = "c://test_cases//rules.txt";
    String fileFrom = "c://test_cases//in.txt";
    String fileTo = "c://test_cases//out.txt";

    public List<Rule> rulesFromFile()
    {
        List<Rule> rules = new LinkedList<>();
        List<String> facts = new LinkedList<>();
        String goal;
        String line;

        int rule_number = 1;
        String consequent;
        List<String> antecedents;

        String[] words;

        try (BufferedReader br = new BufferedReader(new FileReader(rulesFile))) {
            while ((line = br.readLine()) != null) {
                words = line.split(" ");

                consequent = "";
                antecedents = new LinkedList<>();

                line = removeComments(line);
                words = line.split(" ");

                for (int j=0; j<words.length; j++)
                {
                    if (j==0)
                    {
                        consequent = words[j];
                        continue;
                    }
                    antecedents.add(words[j]);
                }
                rules.add(new Rule(consequent, antecedents, rule_number));
                rule_number++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rules;
    }

    public AbstractChaining2 readFromFile(String chainingType)
    {
        List<String> fileLines = new LinkedList<>();
        int rules_from = -1;
        int rules_to = -1;
        int facts_at = -1;
        int goal_at = -1;
        try (BufferedReader br = new BufferedReader(new FileReader(fileFrom))) {

            String line;
            int line_index = 0;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("1) Rules"))
                {
                    rules_from = line_index + 1;
                }
                else if (line.startsWith("2) Facts"))
                {
                    rules_to = line_index - 1;
                    facts_at = line_index + 1;
                }
                else if (line.startsWith("3) Goal"))
                {
                    goal_at = line_index + 1;
                }
                fileLines.add(line);
                line_index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Rule> rules = new LinkedList<>();
        List<String> facts = new LinkedList<>();
        String goal;
        String line;

        int rule_number = 1;
        String consequent;
        List<String> antecedents;

        String[] words;

        for (int i = rules_from; i <= rules_to; i++)
        {
            consequent = "";
            antecedents = new LinkedList<>();

            line = removeComments(fileLines.get(i));
            words = line.split(" ");

            for (int j=0; j<words.length; j++)
            {
                if (j==0)
                {
                    consequent = words[j];
                    continue;
                }
                antecedents.add(words[j]);
            }
            rules.add(new Rule(consequent, antecedents, rule_number));
            //writeToFile(consequent + "/" + antecedents + "/" + rule_number);
            rule_number++;
        }

        line = removeComments(fileLines.get(facts_at));
        for(String fact : line.split(" "))
        {
            facts.add(fact);
        }

        line = removeComments(fileLines.get(goal_at));
        goal = line.split(" ")[0];

        /*switch(chainingType.toLowerCase())
        {
            case "backward": return new BackwardChaining(goal, rules, facts, new StringBuilder());
            case "forward": return new ForwardChaining(goal, rules, facts, new StringBuilder());
            default: return null;
        }*/
        switch(chainingType.toLowerCase())
        {
            case "backward": return new BackwardChaining2(new Data(goal, rules, facts, chainingType.toLowerCase()));
            case "forward": return new ForwardChaining2(new Data(goal, rules, facts, chainingType.toLowerCase()));
            default: return null;
        }
    }

    private String removeComments(String line) {
        int comment_index = line.indexOf("//");

        switch (comment_index) {
            case -1:
                return line;
            default:
                return line.substring(0, comment_index);
        }
    }

    public void writeToFile(String result)
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileTo))) {

            bw.write(result);

            // no need to close it.
            //bw.close();

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

}
