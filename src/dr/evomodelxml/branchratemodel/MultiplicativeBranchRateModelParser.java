package dr.evomodelxml.branchratemodel;

import dr.evomodel.branchratemodel.AbstractBranchRateModel;
import dr.evomodel.branchratemodel.MultiplicativeBranchRateModel;
import dr.xml.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xiang Ji
 * @author Karthik Gangavarapu
 * @author Marc Suchard
 */
public class MultiplicativeBranchRateModelParser extends AbstractXMLObjectParser {

    public static final String RATE_EPOCH_ARBITRARY_BRANCH_RATES = "multiplicativeBranchRates";

    @Override
    public Object parseXMLObject(XMLObject xo) throws XMLParseException {
        List<AbstractBranchRateModel> branchRateModels = new ArrayList<>();
        for (int i = 0; i < xo.getChildCount(); i++) {
            branchRateModels.add((AbstractBranchRateModel) xo.getChild(i));
        }
        return new MultiplicativeBranchRateModel(branchRateModels);
    }

    public XMLSyntaxRule[] getSyntaxRules() {
        return rules;
    }

    private final XMLSyntaxRule[] rules = {
            new ElementRule(AbstractBranchRateModel.class, 2, 2),
    };

    @Override
    public String getParserDescription() {
        return null;
    }

    @Override
    public Class getReturnType() {
        return MultiplicativeBranchRateModel.class;
    }

    @Override
    public String getParserName() {
        return RATE_EPOCH_ARBITRARY_BRANCH_RATES;
    }
}
