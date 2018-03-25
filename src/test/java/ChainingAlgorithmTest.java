import data.entity.Antecedent;
import data.entity.Rule;
import data.entity.RuleAntecedent;
import org.junit.Test;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChainingAlgorithmTest {
    /*@Test
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
    }*/

    /*@Test
    public void sortingTest(){
        List<Antecedent> antecedents = new LinkedList<>();
        antecedents.add(null);
        antecedents.add(null);
        antecedents.add(null);

        List<RuleAntecedent> list = new ArrayList<>();
        Rule rule = new Rule();
        Antecedent antecedent = new Antecedent();

        antecedent.setName("B");
        RuleAntecedent ruleAntecedent = new RuleAntecedent(rule, antecedent);
        ruleAntecedent.setPosition(3L);
        list.add(ruleAntecedent);
        antecedents.set(2, antecedent);

        antecedent = new Antecedent();
        antecedent.setName("C");
        ruleAntecedent = new RuleAntecedent(rule, antecedent);
        ruleAntecedent.setPosition(1L);
        list.add(ruleAntecedent);
        antecedents.set(0, antecedent);

        antecedent = new Antecedent();
        antecedent.setName("X");
        ruleAntecedent = new RuleAntecedent(rule, antecedent);
        ruleAntecedent.setPosition(2L);
        list.add(ruleAntecedent);
        antecedents.set(1, antecedent);

        rule.setRuleAntecedents(list);
        rule.orderAntecedents();

        assertEquals(antecedents, rule.getAntecedents());
    }*/
}
