/*
 * HamiltonianMonteCarloOperatorParser.java
 *
 * Copyright (c) 2002-2017 Alexei Drummond, Andrew Rambaut and Marc Suchard
 *
 * This file is part of BEAST.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * BEAST is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 *  BEAST is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with BEAST; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package dr.inferencexml.operators.hmc;

import dr.inference.hmc.GradientWrtParameterProvider;
import dr.inference.hmc.PrecisionColumnProvider;
import dr.inference.hmc.PrecisionMatrixVectorProductProvider;
import dr.inference.model.Parameter;
import dr.inference.operators.CoercionMode;
import dr.inference.operators.MCMCOperator;
import dr.inference.operators.hmc.AbstractParticleOperator;
import dr.inference.operators.hmc.ZigZagOperator;
import dr.xml.*;

import static dr.inferencexml.operators.hmc.BouncyParticleOperatorParser.parseMask;
import static dr.inferencexml.operators.hmc.BouncyParticleOperatorParser.parseRuntimeOptions;

/**
 * @author Aki Nishimura
 * @author Zhenyu Zhang
 * @author Marc A. Suchard
 */

public class ZigZagOperatorParser extends AbstractXMLObjectParser {

    private final static String ZIG_ZAG_PARSER = "zigZagOperator";

    @Override
    public String getParserName() {
        return ZIG_ZAG_PARSER;
    }

    @Override
    public Object parseXMLObject(XMLObject xo) throws XMLParseException {

        double weight = xo.getDoubleAttribute(MCMCOperator.WEIGHT);

        @SuppressWarnings("unused") CoercionMode coercionMode = CoercionMode.parseMode(xo);

        GradientWrtParameterProvider derivative =
                (GradientWrtParameterProvider) xo.getChild(GradientWrtParameterProvider.class);

        PrecisionMatrixVectorProductProvider productProvider = (PrecisionMatrixVectorProductProvider)
                xo.getChild(PrecisionMatrixVectorProductProvider.class);

        PrecisionColumnProvider columnProvider = (PrecisionColumnProvider)
                xo.getChild(PrecisionColumnProvider.class);

        Parameter mask = parseMask(xo);
        AbstractParticleOperator.Options runtimeOptions = parseRuntimeOptions(xo);

        return new ZigZagOperator(derivative, productProvider, columnProvider, weight, runtimeOptions, mask);
    }

    @Override
    public XMLSyntaxRule[] getSyntaxRules() {
        return XMLSyntaxRule.Utils.concatenate(BouncyParticleOperatorParser.rules, additionalRules);
    }

    private final XMLSyntaxRule[] additionalRules = {
            new ElementRule(PrecisionColumnProvider.class),
    };

    @Override
    public String getParserDescription() {
        return "Returns a bouncy particle transition kernel for truncated normals";
    }

    @Override
    public Class getReturnType() {
        return ZigZagOperator.class;
    }
}