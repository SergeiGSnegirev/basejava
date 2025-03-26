package com.urise.webapp.storage;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Storage Test Suite")
@SelectPackages("com.urise.webapp.storage")
public class AllStorageTest {
}