package com.protsenko.test.parser;

import com.protsenko.test.entity.VariableDeclaration;

import java.util.Map;

public interface VarScopes
{
    Map<String, VariableDeclaration> getDeclarationVars();
    void addVarsIntoScope(VariableDeclaration newVar);
}
