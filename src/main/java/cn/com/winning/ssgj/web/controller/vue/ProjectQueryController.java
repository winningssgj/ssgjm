e>.gitattributes</code> (which is in the parent
directory), and finds that the first line matches, but
<code>t/.gitattributes</code> file already decided how <code>merge</code>, <code>foo</code>
and <code>bar</code> attributes should be given to this path, so it
leaves <code>foo</code> and <code>bar</code> unset.  Attribute <code>baz</code> is set.</p>
</li>
<li>
<p>Finally it examines <code>$GIT_DIR/info/attributes</code>.  This file
is used to override the in-tree settings.  The first line is
a match, and <code>foo</code> is set, <code>bar</code> is reverted to unspecified
state, and <code>baz</code> is unset.</p>
</li>
</ol>
</div>
<div class="paragraph">
<p>As the result, the attributes assignment to <code>t/abc</code> becomes:</p>
</div>
<div class="listingblock">
<div class="content">
<pre>foo	set to true
bar	unspecified
baz	set to false
merge	set to string value "filfre"
frotz	unspecified</pre>
</div>
</div>
</div>
</div>
<div class="sect1">
<h2 id="_see_also">SEE ALSO</h2>
<div class="sectionbody">
<div class="paragraph">
<p><a href="git-check-attr.html">git-check-attr</a>(1).</p>
</div>
</div>
</div>
<div class="sect1">
<h2 id="_git">GIT</h2>
<div class="sectionbody">
<div class="paragraph">
<p>Part of the <a href="git.html">git</a>(1) suite</p>
</div>
</div>
</div>
</div>
<div id="footer">
<div id="footer-text">
Last updated 2018-04-03 11:15:57 Coordinated Universal Time
</div>
</div>
</body>
</html>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                