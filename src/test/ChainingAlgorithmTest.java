package test;

import main.chaining.AbstractChaining;
import main.chaining.ForwardChaining;
import main.data.Data;
import main.data.Rule;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ChainingAlgorithmTest {
    @Test
    public void testForwardChaining() throws Exception {
        List<Rule> rules;
        List<String> antecedents;
        Rule rule;
        List<String> facts;

        rules = new ArrayList<>();

        antecedents = new ArrayList<>();
        antecedents.add("B");
        rule = new Rule("A", antecedents, 1);
        rules.add(rule);

        antecedents = new ArrayList<>();
        antecedents.add("C");
        rule = new Rule("A", antecedents, 2);
        rules.add(rule);

        antecedents = new ArrayList<>();
        antecedents.add("D");
        rule = new Rule("C", antecedents, 3);
        rules.add(rule);

        antecedents = new ArrayList<>();
        antecedents.add("E");
        rule = new Rule("D", antecedents, 4);
        rules.add(rule);

        antecedents = new ArrayList<>();
        antecedents.add("E");
        rule = new Rule("B", antecedents, 5);
        rules.add(rule);

        facts = new ArrayList<>();

        facts.add("E");

        AbstractChaining chainingAlgorithm = new ForwardChaining(new Data("A", rules, facts, "forward"));
        chainingAlgorithm.execute();
    }
}
