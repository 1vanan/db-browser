package com.example.dbbrowser;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

/*
  To run all tests in one click.
*/
@RunWith(JUnitPlatform.class)
@SelectPackages("com.example.dbbrowser")
public class DbBrowserTestSuite {
}
