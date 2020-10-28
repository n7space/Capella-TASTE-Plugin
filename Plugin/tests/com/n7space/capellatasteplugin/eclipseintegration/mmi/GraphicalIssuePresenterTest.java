// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.eclipseintegration.mmi;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.n7space.capellatasteplugin.utils.Issue;
import com.n7space.capellatasteplugin.utils.Issue.Kind;

public class GraphicalIssuePresenterTest {

	protected GraphicalIssuePresenter presenter;
	protected Issue error1;
	protected Issue error2;
	protected Issue warning1;
	protected Issue warning2;
	protected Issue info1;
	protected Issue info2;
	protected List<Issue> mixedIssues;

	@Before
	public void setUp() throws Exception {
		presenter = new GraphicalIssuePresenter();
		error1 = new Issue(Kind.Error, "Error1", null);
		error2 = new Issue(Kind.Error, "Error2", null);

		warning1 = new Issue(Kind.Warning, "Warning1", null);
		warning2 = new Issue(Kind.Warning, "Warning2", null);

		info1 = new Issue(Kind.Info, "Info1", null);
		info2 = new Issue(Kind.Info, "Info2", null);

		mixedIssues = new LinkedList<Issue>();
		mixedIssues.add(info1);
		mixedIssues.add(warning1);
		mixedIssues.add(info2);
		mixedIssues.add(error1);
		mixedIssues.add(error2);
		mixedIssues.add(warning2);
	}

	@Test
	public void testGetIssuesOfType() {
		final List<Issue> errors = presenter.getIssuesOfType(mixedIssues, Kind.Error);
		final List<Issue> warnings = presenter.getIssuesOfType(mixedIssues, Kind.Warning);
		final List<Issue> infos = presenter.getIssuesOfType(mixedIssues, Kind.Info);

		assertEquals(errors.size(), 2);
		assertEquals(errors.get(0), error1);
		assertEquals(errors.get(1), error2);
		assertEquals(warnings.size(), 2);
		assertEquals(warnings.get(0), warning1);
		assertEquals(warnings.get(1), warning2);
		assertEquals(infos.size(), 2);
		assertEquals(infos.get(0), info1);
		assertEquals(infos.get(1), info2);
	}

	@Test
	public void testGetSortedIssues() {
		final List<Issue> issues = presenter.getSortedIssues(mixedIssues);
		assertEquals(issues.size(), 6);
		assertEquals(issues.get(0), error1);
		assertEquals(issues.get(1), error2);
		assertEquals(issues.get(2), warning1);
		assertEquals(issues.get(3), warning2);
		assertEquals(issues.get(4), info1);
		assertEquals(issues.get(5), info2);
	}

}
